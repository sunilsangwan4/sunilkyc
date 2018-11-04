import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INominee } from 'app/shared/model/nominee.model';
import { Principal } from 'app/core';
import { NomineeService } from './nominee.service';

@Component({
    selector: 'jhi-nominee',
    templateUrl: './nominee.component.html'
})
export class NomineeComponent implements OnInit, OnDestroy {
    nominees: INominee[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private nomineeService: NomineeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.nomineeService.query().subscribe(
            (res: HttpResponse<INominee[]>) => {
                this.nominees = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInNominees();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INominee) {
        return item.id;
    }

    registerChangeInNominees() {
        this.eventSubscriber = this.eventManager.subscribe('nomineeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
