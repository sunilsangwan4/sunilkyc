import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBankInformation } from 'app/shared/model/bank-information.model';
import { BankInformationService } from './bank-information.service';
import { IApplicationProspect, ApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-bank-information-update',
    templateUrl: './bank-information-update.component.html'
})
export class BankInformationUpdateComponent implements OnInit {
    bankInformation: IBankInformation;
    isSaving: boolean;
    applicationProspect: IApplicationProspect;
    prospectId: number;

    constructor(
        private jhiAlertService: JhiAlertService,
        private bankInformationService: BankInformationService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bankInformation }) => {
            this.bankInformation = bankInformation;
        });

        this.activatedRoute.params.subscribe(params => {
            this.prospectId = params.prospectId; // --> Name must match wanted parameter
        });
        this.applicationProspectService
            .find(this.prospectId)
            .subscribe(
                (res: HttpResponse<IApplicationProspect>) => (this.applicationProspect = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        this.router.navigate(['nominee', this.applicationProspect.nomineeId, 'edit', this.prospectId]);
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
        result.subscribe((res: HttpResponse<IBankInformation>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(res: HttpResponse<IBankInformation>) {
        alert('Done ,Thank You ! We are happy to add other feature on request');
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
