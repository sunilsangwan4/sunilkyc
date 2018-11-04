import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IIdentityVerification } from 'app/shared/model/identity-verification.model';
import { IdentityVerificationService } from './identity-verification.service';

@Component({
    selector: 'jhi-identity-verification-update',
    templateUrl: './identity-verification-update.component.html'
})
export class IdentityVerificationUpdateComponent implements OnInit {
    identityVerification: IIdentityVerification;
    isSaving: boolean;
    dateOfBirthDp: any;

    constructor(private identityVerificationService: IdentityVerificationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ identityVerification }) => {
            this.identityVerification = identityVerification;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.identityVerification.id !== undefined) {
            this.subscribeToSaveResponse(this.identityVerificationService.update(this.identityVerification));
        } else {
            this.subscribeToSaveResponse(this.identityVerificationService.create(this.identityVerification));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIdentityVerification>>) {
        result.subscribe(
            (res: HttpResponse<IIdentityVerification>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
