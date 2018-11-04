import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPersonalInformation } from 'app/shared/model/personal-information.model';
import { Principal } from 'app/core';
import { PersonalInformationService } from './personal-information.service';

@Component({
    selector: 'jhi-personal-information',
    templateUrl: './personal-information.component.html'
})
export class PersonalInformationComponent implements OnInit, OnDestroy {
    personalInformations: IPersonalInformation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private personalInformationService: PersonalInformationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.personalInformationService.query().subscribe(
            (res: HttpResponse<IPersonalInformation[]>) => {
                this.personalInformations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonalInformations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPersonalInformation) {
        return item.id;
    }

    registerChangeInPersonalInformations() {
        this.eventSubscriber = this.eventManager.subscribe('personalInformationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
