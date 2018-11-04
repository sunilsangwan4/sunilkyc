import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEmailVerification } from 'app/shared/model/email-verification.model';
import { EmailVerificationService } from './email-verification.service';

@Component({
    selector: 'jhi-email-verification-update',
    templateUrl: './email-verification-update.component.html'
})
export class EmailVerificationUpdateComponent implements OnInit {
    emailVerification: IEmailVerification;
    isSaving: boolean;

    constructor(private emailVerificationService: EmailVerificationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ emailVerification }) => {
            this.emailVerification = emailVerification;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.emailVerification.id !== undefined) {
            this.subscribeToSaveResponse(this.emailVerificationService.update(this.emailVerification));
        } else {
            this.subscribeToSaveResponse(this.emailVerificationService.create(this.emailVerification));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmailVerification>>) {
        result.subscribe((res: HttpResponse<IEmailVerification>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
