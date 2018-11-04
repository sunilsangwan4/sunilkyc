import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmailVerification } from 'app/shared/model/email-verification.model';

@Component({
    selector: 'jhi-email-verification-detail',
    templateUrl: './email-verification-detail.component.html'
})
export class EmailVerificationDetailComponent implements OnInit {
    emailVerification: IEmailVerification;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ emailVerification }) => {
            this.emailVerification = emailVerification;
        });
    }

    previousState() {
        window.history.back();
    }
}
