import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPersonalInformation } from 'app/shared/model/personal-information.model';
import { PersonalInformationService } from './personal-information.service';
import { IApplicationProspect, ApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-personal-information-update',
    templateUrl: './personal-information-update.component.html'
})
export class PersonalInformationUpdateComponent implements OnInit {
    personalInformation: IPersonalInformation;
    isSaving: boolean;
    isIndianTaxPayer: boolean;

    applicationProspect: IApplicationProspect;
    prospectId: number;

    constructor(
        private jhiAlertService: JhiAlertService,
        private personalInformationService: PersonalInformationService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personalInformation }) => {
            this.personalInformation = personalInformation;
        });

        this.activatedRoute.params.subscribe(params => {
            this.prospectId = params.prospectId; // --> Name must match wanted parameter
        });
        this.personalInformation.indianTaxPayer = 'Y';
        this.applicationProspectService
            .find(this.prospectId)
            .subscribe(
                (res: HttpResponse<IApplicationProspect>) => (this.applicationProspect = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        this.router.navigate(['identity-verification', this.applicationProspect.identityVerificationId, 'edit', this.prospectId]);
    }

    save() {
        this.isSaving = true;
        if (this.personalInformation.id !== undefined) {
            this.subscribeToSaveResponse(this.personalInformationService.update(this.personalInformation));
        } else {
            this.subscribeToSaveResponse(this.personalInformationService.create(this.personalInformation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPersonalInformation>>) {
        result.subscribe(
            (res: HttpResponse<IPersonalInformation>) => this.onSaveSuccess(res.body),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(personalInformation: IPersonalInformation) {
        this.isSaving = false;
        this.applicationProspect.personalInformationId = personalInformation.id;
        this.applicationProspectService
            .update(this.applicationProspect)
            .subscribe(
                (res: HttpResponse<ApplicationProspect>) => (this.applicationProspect = res.body),
                (res: HttpErrorResponse) => this.onSaveError()
            );
        if (this.applicationProspect.tradingInfoId !== null) {
            this.router.navigate(['trading-info', this.applicationProspect.tradingInfoId, 'edit', this.prospectId]);
        } else {
            this.router.navigate(['trading-info/new', this.prospectId]);
        }
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackApplicationProspectById(index: number, item: IApplicationProspect) {
        return item.id;
    }
}
