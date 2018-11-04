import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankInformation } from 'app/shared/model/bank-information.model';

@Component({
    selector: 'jhi-bank-information-detail',
    templateUrl: './bank-information-detail.component.html'
})
export class BankInformationDetailComponent implements OnInit {
    bankInformation: IBankInformation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bankInformation }) => {
            this.bankInformation = bankInformation;
        });
    }

    previousState() {
        window.history.back();
    }
}
