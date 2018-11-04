import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIdentityVerification } from 'app/shared/model/identity-verification.model';
import { Principal } from 'app/core';
import { IdentityVerificationService } from './identity-verification.service';

@Component({
    selector: 'jhi-identity-verification',
    templateUrl: './identity-verification.component.html'
})
export class IdentityVerificationComponent implements OnInit, OnDestroy {
    identityVerifications: IIdentityVerification[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private identityVerificationService: IdentityVerificationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.identityVerificationService.query().subscribe(
            (res: HttpResponse<IIdentityVerification[]>) => {
                this.identityVerifications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIdentityVerifications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIdentityVerification) {
        return item.id;
    }

    registerChangeInIdentityVerifications() {
        this.eventSubscriber = this.eventManager.subscribe('identityVerificationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
