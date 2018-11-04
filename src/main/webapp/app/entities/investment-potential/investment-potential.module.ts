import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    InvestmentPotentialComponent,
    InvestmentPotentialDetailComponent,
    InvestmentPotentialUpdateComponent,
    InvestmentPotentialDeletePopupComponent,
    InvestmentPotentialDeleteDialogComponent,
    investmentPotentialRoute,
    investmentPotentialPopupRoute
} from './';

const ENTITY_STATES = [...investmentPotentialRoute, ...investmentPotentialPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvestmentPotentialComponent,
        InvestmentPotentialDetailComponent,
        InvestmentPotentialUpdateComponent,
        InvestmentPotentialDeleteDialogComponent,
        InvestmentPotentialDeletePopupComponent
    ],
    entryComponents: [
        InvestmentPotentialComponent,
        InvestmentPotentialUpdateComponent,
        InvestmentPotentialDeleteDialogComponent,
        InvestmentPotentialDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5InvestmentPotentialModule {}
