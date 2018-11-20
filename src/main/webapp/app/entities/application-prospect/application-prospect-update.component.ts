import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { IApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from './application-prospect.service';
import { IPersonalInformation } from 'app/shared/model/personal-information.model';
import { PersonalInformationService } from 'app/entities/personal-information';
import { IInvestmentPotential } from 'app/shared/model/investment-potential.model';
import { InvestmentPotentialService } from 'app/entities/investment-potential';
import { INominee } from 'app/shared/model/nominee.model';
import { NomineeService } from 'app/entities/nominee';
import { ITradingInfo } from 'app/shared/model/trading-info.model';
import { TradingInfoService } from 'app/entities/trading-info';
import { IDepositoryInfo } from 'app/shared/model/depository-info.model';
import { DepositoryInfoService } from 'app/entities/depository-info';
import { StateStorageService, LoginService } from 'app/core';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-application-prospect-update',
    templateUrl: './application-prospect-update.component.html'
})
export class ApplicationProspectUpdateComponent implements OnInit {
    applicationProspect: IApplicationProspect;
    isSaving: boolean;
    loggedIn: boolean;
    otpSent: boolean;
    verified: boolean;
    otp: number;
    filledOTP: number;

    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute,
        private localStorage: LocalStorageService,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.loggedIn = false;
        this.otpSent = false;
        this.verified = false;
        this.otp = 222222;
        this.activatedRoute.data.subscribe(({ applicationProspect }) => {
            this.applicationProspect = applicationProspect;
        });
    }

    sendOTP() {
        this.otpSent = true;
    }

    verifyOTP() {
        if (this.otp == this.filledOTP) {
            this.verified = true;
        }
    }
    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.applicationProspect.id !== undefined) {
            this.subscribeToSaveResponse(this.applicationProspectService.update(this.applicationProspect));
        } else {
            this.subscribeToSaveResponse(this.applicationProspectService.create(this.applicationProspect));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationProspect>>) {
        result.subscribe(
            (res: HttpResponse<IApplicationProspect>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(res: HttpResponse<IApplicationProspect>) {
        this.isSaving = false;
        this.localStorage.store('prospectId', res.body.id);
        if (this.applicationProspect.identityVerificationId !== undefined) {
            this.router.navigate([
                'identity-verification',
                this.applicationProspect.identityVerificationId,
                'edit',
                this.localStorage.retrieve('prospectId')
            ]);
        } else {
            this.router.navigate(['identity-verification/new', this.localStorage.retrieve('prospectId')]);
        }
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
