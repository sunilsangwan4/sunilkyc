import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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

    applicationProspect: IApplicationProspect;
    prospectId: number;
    constructor(
        private jhiAlertService: JhiAlertService,
        private depositoryInfoService: DepositoryInfoService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ depositoryInfo }) => {
            this.depositoryInfo = depositoryInfo;
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
        this.depositoryInfo.haveAccountWithOtherDP = false;
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
        result.subscribe((res: HttpResponse<IDepositoryInfo>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(res: HttpResponse<IDepositoryInfo>) {
        this.isSaving = false;
        this.applicationProspect.depositoryId = res.body.id;
        this.applicationProspectService.update(this.applicationProspect);
        this.router.navigate(['investment-potential/new', this.prospectId]);
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
