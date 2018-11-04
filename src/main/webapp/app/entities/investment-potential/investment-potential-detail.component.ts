import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvestmentPotential } from 'app/shared/model/investment-potential.model';

@Component({
    selector: 'jhi-investment-potential-detail',
    templateUrl: './investment-potential-detail.component.html'
})
export class InvestmentPotentialDetailComponent implements OnInit {
    investmentPotential: IInvestmentPotential;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ investmentPotential }) => {
            this.investmentPotential = investmentPotential;
        });
    }

    previousState() {
        window.history.back();
    }
}
