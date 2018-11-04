import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBankInformation } from 'app/shared/model/bank-information.model';
import { BankInformationService } from './bank-information.service';
import { IApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-bank-information-update',
    templateUrl: './bank-information-update.component.html'
})
export class BankInformationUpdateComponent implements OnInit {
    bankInformation: IBankInformation;
    isSaving: boolean;

    applicationprospects: IApplicationProspect[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bankInformationService: BankInformationService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bankInformation }) => {
            this.bankInformation = bankInformation;
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
        if (this.bankInformation.id !== undefined) {
            this.subscribeToSaveResponse(this.bankInformationService.update(this.bankInformation));
        } else {
            this.subscribeToSaveResponse(this.bankInformationService.create(this.bankInformation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBankInformation>>) {
        result.subscribe((res: HttpResponse<IBankInformation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
