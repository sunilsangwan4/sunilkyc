import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IIdentityVerification } from 'app/shared/model/identity-verification.model';
import { IdentityVerificationService } from './identity-verification.service';
import { ApplicationProspectService } from 'app/entities/application-prospect';
import { IApplicationProspect, ApplicationProspect } from 'app/shared/model/application-prospect.model';
import { map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-identity-verification-update',
    templateUrl: './identity-verification-update.component.html'
})
export class IdentityVerificationUpdateComponent implements OnInit {
    identityVerification: IIdentityVerification;
    isSaving: boolean;
    dateOfBirthDp: any;
    prospectId: number;
    applicationProspect: IApplicationProspect;

    constructor(
        private identityVerificationService: IdentityVerificationService,
        private applicationProspectService: ApplicationProspectService,
        private jhiAlertService: JhiAlertService,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.activatedRoute.params.subscribe(params => {
            this.prospectId = params.prospectId; // --> Name must match wanted parameter
        });
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ identityVerification }) => {
            this.identityVerification = identityVerification;
        });

        this.applicationProspectService
            .find(this.prospectId)
            .subscribe(
                (res: HttpResponse<IApplicationProspect>) => (this.applicationProspect = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.identityVerification.id !== undefined) {
            this.subscribeToSaveResponse(this.identityVerificationService.update(this.identityVerification));
        } else {
            this.subscribeToSaveResponse(this.identityVerificationService.create(this.identityVerification));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIdentityVerification>>) {
        result.subscribe(
            (res: HttpResponse<IIdentityVerification>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(res: HttpResponse<IIdentityVerification>) {
        this.isSaving = false;
        this.applicationProspect.identityVerificationId = res.body.id;
        this.applicationProspectService.update(this.applicationProspect);
        this.router.navigate(['personal-information/new', this.prospectId]);
    }
    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    private onSaveError() {
        this.isSaving = false;
    }
}
