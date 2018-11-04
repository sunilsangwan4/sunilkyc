import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITokenVerification } from 'app/shared/model/token-verification.model';
import { TokenVerificationService } from './token-verification.service';

@Component({
    selector: 'jhi-token-verification-update',
    templateUrl: './token-verification-update.component.html'
})
export class TokenVerificationUpdateComponent implements OnInit {
    tokenVerification: ITokenVerification;
    isSaving: boolean;

    constructor(private tokenVerificationService: TokenVerificationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tokenVerification }) => {
            this.tokenVerification = tokenVerification;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tokenVerification.id !== undefined) {
            this.subscribeToSaveResponse(this.tokenVerificationService.update(this.tokenVerification));
        } else {
            this.subscribeToSaveResponse(this.tokenVerificationService.create(this.tokenVerification));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITokenVerification>>) {
        result.subscribe((res: HttpResponse<ITokenVerification>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
