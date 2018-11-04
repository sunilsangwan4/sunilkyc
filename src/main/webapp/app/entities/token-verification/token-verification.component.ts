import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITokenVerification } from 'app/shared/model/token-verification.model';
import { Principal } from 'app/core';
import { TokenVerificationService } from './token-verification.service';

@Component({
    selector: 'jhi-token-verification',
    templateUrl: './token-verification.component.html'
})
export class TokenVerificationComponent implements OnInit, OnDestroy {
    tokenVerifications: ITokenVerification[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tokenVerificationService: TokenVerificationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.tokenVerificationService.query().subscribe(
            (res: HttpResponse<ITokenVerification[]>) => {
                this.tokenVerifications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTokenVerifications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITokenVerification) {
        return item.id;
    }

    registerChangeInTokenVerifications() {
        this.eventSubscriber = this.eventManager.subscribe('tokenVerificationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
