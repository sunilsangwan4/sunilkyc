import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepositoryInfo } from 'app/shared/model/depository-info.model';

@Component({
    selector: 'jhi-depository-info-detail',
    templateUrl: './depository-info-detail.component.html'
})
export class DepositoryInfoDetailComponent implements OnInit {
    depositoryInfo: IDepositoryInfo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depositoryInfo }) => {
            this.depositoryInfo = depositoryInfo;
        });
    }

    previousState() {
        window.history.back();
    }
}
