import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIdentityVerification } from 'app/shared/model/identity-verification.model';

@Component({
    selector: 'jhi-identity-verification-detail',
    templateUrl: './identity-verification-detail.component.html'
})
export class IdentityVerificationDetailComponent implements OnInit {
    identityVerification: IIdentityVerification;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ identityVerification }) => {
            this.identityVerification = identityVerification;
        });
    }

    previousState() {
        window.history.back();
    }
}
