import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    DepositoryInfoComponent,
    DepositoryInfoDetailComponent,
    DepositoryInfoUpdateComponent,
    DepositoryInfoDeletePopupComponent,
    DepositoryInfoDeleteDialogComponent,
    depositoryInfoRoute,
    depositoryInfoPopupRoute
} from './';

const ENTITY_STATES = [...depositoryInfoRoute, ...depositoryInfoPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DepositoryInfoComponent,
        DepositoryInfoDetailComponent,
        DepositoryInfoUpdateComponent,
        DepositoryInfoDeleteDialogComponent,
        DepositoryInfoDeletePopupComponent
    ],
    entryComponents: [
        DepositoryInfoComponent,
        DepositoryInfoUpdateComponent,
        DepositoryInfoDeleteDialogComponent,
        DepositoryInfoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5DepositoryInfoModule {}
