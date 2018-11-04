import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITradingInfo } from 'app/shared/model/trading-info.model';

@Component({
    selector: 'jhi-trading-info-detail',
    templateUrl: './trading-info-detail.component.html'
})
export class TradingInfoDetailComponent implements OnInit {
    tradingInfo: ITradingInfo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tradingInfo }) => {
            this.tradingInfo = tradingInfo;
        });
    }

    previousState() {
        window.history.back();
    }
}
