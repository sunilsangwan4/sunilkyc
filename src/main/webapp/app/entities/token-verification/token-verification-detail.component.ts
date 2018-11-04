import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITokenVerification } from 'app/shared/model/token-verification.model';

@Component({
    selector: 'jhi-token-verification-detail',
    templateUrl: './token-verification-detail.component.html'
})
export class TokenVerificationDetailComponent implements OnInit {
    tokenVerification: ITokenVerification;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tokenVerification }) => {
            this.tokenVerification = tokenVerification;
        });
    }

    previousState() {
        window.history.back();
    }
}
