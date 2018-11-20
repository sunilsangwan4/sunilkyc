import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { INominee } from 'app/shared/model/nominee.model';
import { NomineeService } from './nominee.service';
import { IApplicationProspect, ApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from 'app/entities/application-prospect';

@Component({
    selector: 'jhi-nominee-update',
    templateUrl: './nominee-update.component.html'
})
export class NomineeUpdateComponent implements OnInit {
    nominee: INominee;
    isSaving: boolean;
    dateOfBirthDp: any;
    applicationProspect: IApplicationProspect;
    prospectId: number;

    constructor(
        private jhiAlertService: JhiAlertService,
        private nomineeService: NomineeService,
        private applicationProspectService: ApplicationProspectService,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ nominee }) => {
            this.nominee = nominee;
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
        this.router.navigate(['investment-potential', this.applicationProspect.investmentPotentialId, 'edit', this.prospectId]);
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
        result.subscribe((res: HttpResponse<INominee>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(res: HttpResponse<INominee>) {
        this.applicationProspect.nomineeId = res.body.id;
        this.applicationProspectService
            .update(this.applicationProspect)
            .subscribe(
                (response: HttpResponse<ApplicationProspect>) => (this.applicationProspect = response.body),
                (response: HttpErrorResponse) => this.onSaveError()
            );

        this.router.navigate(['bank-information/new', this.prospectId]);
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
