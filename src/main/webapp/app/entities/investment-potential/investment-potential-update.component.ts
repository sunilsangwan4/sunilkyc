import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IInvestmentPotential } from 'app/shared/model/investment-potential.model';
import { InvestmentPotentialService } from './investment-potential.service';
import { IApplicationProspect, ApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-investment-potential-update',
    templateUrl: './investment-potential-update.component.html'
})
export class InvestmentPotentialUpdateComponent implements OnInit {
    investmentPotential: IInvestmentPotential;
    isSaving: boolean;

    applicationProspect: IApplicationProspect;
    prospectId: number;

    networthDeclarationDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private investmentPotentialService: InvestmentPotentialService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ investmentPotential }) => {
            this.investmentPotential = investmentPotential;
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
        this.investmentPotential.pepRelative = 'N';
    }

    previousState() {
        this.router.navigate(['depository-info', this.applicationProspect.depositoryId, 'edit', this.prospectId]);
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
        result.subscribe(
            (res: HttpResponse<IInvestmentPotential>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(res: HttpResponse<IInvestmentPotential>) {
        this.applicationProspect.investmentPotentialId = res.body.id;
        this.applicationProspectService
            .update(this.applicationProspect)
            .subscribe(
                (response: HttpResponse<ApplicationProspect>) => (this.applicationProspect = response.body),
                (response: HttpErrorResponse) => this.onSaveError()
            );
        if (this.applicationProspect.nomineeId !== null) {
            this.router.navigate(['nominee', this.applicationProspect.nomineeId, 'edit', this.prospectId]);
        } else {
            this.router.navigate(['nominee/new', this.prospectId]);
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
