import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGroupCd } from 'app/shared/model/group-cd.model';
import { Principal } from 'app/core';
import { GroupCdService } from './group-cd.service';

@Component({
    selector: 'jhi-group-cd',
    templateUrl: './group-cd.component.html'
})
export class GroupCdComponent implements OnInit, OnDestroy {
    groupCds: IGroupCd[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private groupCdService: GroupCdService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.groupCdService.query().subscribe(
            (res: HttpResponse<IGroupCd[]>) => {
                this.groupCds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGroupCds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGroupCd) {
        return item.id;
    }

    registerChangeInGroupCds() {
        this.eventSubscriber = this.eventManager.subscribe('groupCdListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
