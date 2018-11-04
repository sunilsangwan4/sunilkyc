import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBankInformation } from 'app/shared/model/bank-information.model';
import { Principal } from 'app/core';
import { BankInformationService } from './bank-information.service';

@Component({
    selector: 'jhi-bank-information',
    templateUrl: './bank-information.component.html'
})
export class BankInformationComponent implements OnInit, OnDestroy {
    bankInformations: IBankInformation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bankInformationService: BankInformationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bankInformationService.query().subscribe(
            (res: HttpResponse<IBankInformation[]>) => {
                this.bankInformations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBankInformations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBankInformation) {
        return item.id;
    }

    registerChangeInBankInformations() {
        this.eventSubscriber = this.eventManager.subscribe('bankInformationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
