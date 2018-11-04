import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPersonalInformation } from 'app/shared/model/personal-information.model';
import { PersonalInformationService } from './personal-information.service';
import { IApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-personal-information-update',
    templateUrl: './personal-information-update.component.html'
})
export class PersonalInformationUpdateComponent implements OnInit {
    personalInformation: IPersonalInformation;
    isSaving: boolean;

    applicationprospects: IApplicationProspect[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private personalInformationService: PersonalInformationService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personalInformation }) => {
            this.personalInformation = personalInformation;
        });
        this.applicationProspectService.query().subscribe(
            (res: HttpResponse<IApplicationProspect[]>) => {
                this.applicationprospects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
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
        result.subscribe((res: HttpResponse<IPersonalInformation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
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
