import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    BankInformationComponent,
    BankInformationDetailComponent,
    BankInformationUpdateComponent,
    BankInformationDeletePopupComponent,
    BankInformationDeleteDialogComponent,
    bankInformationRoute,
    bankInformationPopupRoute
} from './';

const ENTITY_STATES = [...bankInformationRoute, ...bankInformationPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BankInformationComponent,
        BankInformationDetailComponent,
        BankInformationUpdateComponent,
        BankInformationDeleteDialogComponent,
        BankInformationDeletePopupComponent
    ],
    entryComponents: [
        BankInformationComponent,
        BankInformationUpdateComponent,
        BankInformationDeleteDialogComponent,
        BankInformationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5BankInformationModule {}
