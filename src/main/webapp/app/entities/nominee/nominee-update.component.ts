import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { INominee } from 'app/shared/model/nominee.model';
import { NomineeService } from './nominee.service';
import { IApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-nominee-update',
    templateUrl: './nominee-update.component.html'
})
export class NomineeUpdateComponent implements OnInit {
    nominee: INominee;
    isSaving: boolean;

    applicationprospects: IApplicationProspect[];
    dateOfBirthDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private nomineeService: NomineeService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ nominee }) => {
            this.nominee = nominee;
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
        if (this.nominee.id !== undefined) {
            this.subscribeToSaveResponse(this.nomineeService.update(this.nominee));
        } else {
            this.subscribeToSaveResponse(this.nomineeService.create(this.nominee));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INominee>>) {
        result.subscribe((res: HttpResponse<INominee>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
