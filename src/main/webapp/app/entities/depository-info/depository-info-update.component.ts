import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDepositoryInfo } from 'app/shared/model/depository-info.model';
import { DepositoryInfoService } from './depository-info.service';
import { IApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-depository-info-update',
    templateUrl: './depository-info-update.component.html'
})
export class DepositoryInfoUpdateComponent implements OnInit {
    depositoryInfo: IDepositoryInfo;
    isSaving: boolean;

    applicationprospects: IApplicationProspect[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private depositoryInfoService: DepositoryInfoService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ depositoryInfo }) => {
            this.depositoryInfo = depositoryInfo;
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
        if (this.depositoryInfo.id !== undefined) {
            this.subscribeToSaveResponse(this.depositoryInfoService.update(this.depositoryInfo));
        } else {
            this.subscribeToSaveResponse(this.depositoryInfoService.create(this.depositoryInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDepositoryInfo>>) {
        result.subscribe((res: HttpResponse<IDepositoryInfo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
