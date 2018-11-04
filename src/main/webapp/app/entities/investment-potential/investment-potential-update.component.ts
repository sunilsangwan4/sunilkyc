import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IInvestmentPotential } from 'app/shared/model/investment-potential.model';
import { InvestmentPotentialService } from './investment-potential.service';
import { IApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-investment-potential-update',
    templateUrl: './investment-potential-update.component.html'
})
export class InvestmentPotentialUpdateComponent implements OnInit {
    investmentPotential: IInvestmentPotential;
    isSaving: boolean;

    applicationprospects: IApplicationProspect[];
    networthDeclarationDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private investmentPotentialService: InvestmentPotentialService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ investmentPotential }) => {
            this.investmentPotential = investmentPotential;
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
        if (this.investmentPotential.id !== undefined) {
            this.subscribeToSaveResponse(this.investmentPotentialService.update(this.investmentPotential));
        } else {
            this.subscribeToSaveResponse(this.investmentPotentialService.create(this.investmentPotential));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInvestmentPotential>>) {
        result.subscribe((res: HttpResponse<IInvestmentPotential>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
