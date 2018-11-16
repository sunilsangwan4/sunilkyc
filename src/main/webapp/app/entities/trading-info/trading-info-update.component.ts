import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITradingInfo } from 'app/shared/model/trading-info.model';
import { TradingInfoService } from './trading-info.service';
import { IApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-trading-info-update',
    templateUrl: './trading-info-update.component.html'
})
export class TradingInfoUpdateComponent implements OnInit {
    tradingInfo: ITradingInfo;
    isSaving: boolean;
    applicationProspect: IApplicationProspect;
    prospectId: number;

    constructor(
        private jhiAlertService: JhiAlertService,
        private tradingInfoService: TradingInfoService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tradingInfo }) => {
            this.tradingInfo = tradingInfo;
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
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tradingInfo.id !== undefined) {
            this.subscribeToSaveResponse(this.tradingInfoService.update(this.tradingInfo));
        } else {
            this.subscribeToSaveResponse(this.tradingInfoService.create(this.tradingInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITradingInfo>>) {
        result.subscribe((res: HttpResponse<ITradingInfo>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(res: HttpResponse<ITradingInfo>) {
        this.applicationProspect.tradingInfoId = res.body.id;
        this.applicationProspectService.update(this.applicationProspect);
        this.router.navigate(['depository-info/new', this.prospectId]);
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
