import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGroupCd } from 'app/shared/model/group-cd.model';
import { GroupCdService } from './group-cd.service';

@Component({
    selector: 'jhi-group-cd-update',
    templateUrl: './group-cd-update.component.html'
})
export class GroupCdUpdateComponent implements OnInit {
    groupCd: IGroupCd;
    isSaving: boolean;

    constructor(private groupCdService: GroupCdService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ groupCd }) => {
            this.groupCd = groupCd;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.groupCd.id !== undefined) {
            this.subscribeToSaveResponse(this.groupCdService.update(this.groupCd));
        } else {
            this.subscribeToSaveResponse(this.groupCdService.create(this.groupCd));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGroupCd>>) {
        result.subscribe((res: HttpResponse<IGroupCd>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
