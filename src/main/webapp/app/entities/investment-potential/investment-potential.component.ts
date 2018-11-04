import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInvestmentPotential } from 'app/shared/model/investment-potential.model';
import { Principal } from 'app/core';
import { InvestmentPotentialService } from './investment-potential.service';

@Component({
    selector: 'jhi-investment-potential',
    templateUrl: './investment-potential.component.html'
})
export class InvestmentPotentialComponent implements OnInit, OnDestroy {
    investmentPotentials: IInvestmentPotential[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private investmentPotentialService: InvestmentPotentialService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.investmentPotentialService.query().subscribe(
            (res: HttpResponse<IInvestmentPotential[]>) => {
                this.investmentPotentials = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInInvestmentPotentials();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInvestmentPotential) {
        return item.id;
    }

    registerChangeInInvestmentPotentials() {
        this.eventSubscriber = this.eventManager.subscribe('investmentPotentialListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
