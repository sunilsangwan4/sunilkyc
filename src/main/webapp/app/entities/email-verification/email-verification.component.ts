import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmailVerification } from 'app/shared/model/email-verification.model';
import { Principal } from 'app/core';
import { EmailVerificationService } from './email-verification.service';

@Component({
    selector: 'jhi-email-verification',
    templateUrl: './email-verification.component.html'
})
export class EmailVerificationComponent implements OnInit, OnDestroy {
    emailVerifications: IEmailVerification[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private emailVerificationService: EmailVerificationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.emailVerificationService.query().subscribe(
            (res: HttpResponse<IEmailVerification[]>) => {
                this.emailVerifications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmailVerifications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmailVerification) {
        return item.id;
    }

    registerChangeInEmailVerifications() {
        this.eventSubscriber = this.eventManager.subscribe('emailVerificationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
