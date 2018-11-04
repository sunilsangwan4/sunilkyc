import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITradingInfo } from 'app/shared/model/trading-info.model';
import { Principal } from 'app/core';
import { TradingInfoService } from './trading-info.service';

@Component({
    selector: 'jhi-trading-info',
    templateUrl: './trading-info.component.html'
})
export class TradingInfoComponent implements OnInit, OnDestroy {
    tradingInfos: ITradingInfo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tradingInfoService: TradingInfoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.tradingInfoService.query().subscribe(
            (res: HttpResponse<ITradingInfo[]>) => {
                this.tradingInfos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTradingInfos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITradingInfo) {
        return item.id;
    }

    registerChangeInTradingInfos() {
        this.eventSubscriber = this.eventManager.subscribe('tradingInfoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
