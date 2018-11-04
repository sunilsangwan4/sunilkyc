import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITypeCd } from 'app/shared/model/type-cd.model';
import { TypeCdService } from './type-cd.service';
import { IGroupCd } from 'app/shared/model/group-cd.model';
import { GroupCdService } from 'app/entities/group-cd';

@Component({
    selector: 'jhi-type-cd-update',
    templateUrl: './type-cd-update.component.html'
})
export class TypeCdUpdateComponent implements OnInit {
    typeCd: ITypeCd;
    isSaving: boolean;

    groupcds: IGroupCd[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private typeCdService: TypeCdService,
        private groupCdService: GroupCdService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeCd }) => {
            this.typeCd = typeCd;
        });
        this.groupCdService.query().subscribe(
            (res: HttpResponse<IGroupCd[]>) => {
                this.groupcds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeCd.id !== undefined) {
            this.subscribeToSaveResponse(this.typeCdService.update(this.typeCd));
        } else {
            this.subscribeToSaveResponse(this.typeCdService.create(this.typeCd));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITypeCd>>) {
        result.subscribe((res: HttpResponse<ITypeCd>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGroupCdById(index: number, item: IGroupCd) {
        return item.id;
    }
}
