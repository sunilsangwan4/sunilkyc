import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationProspect } from 'app/shared/model/application-prospect.model';

@Component({
    selector: 'jhi-application-prospect-detail',
    templateUrl: './application-prospect-detail.component.html'
})
export class ApplicationProspectDetailComponent implements OnInit {
    applicationProspect: IApplicationProspect;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ applicationProspect }) => {
            this.applicationProspect = applicationProspect;
        });
    }

    previousState() {
        window.history.back();
    }
}
