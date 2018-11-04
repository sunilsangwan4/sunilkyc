import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    TokenVerificationComponent,
    TokenVerificationDetailComponent,
    TokenVerificationUpdateComponent,
    TokenVerificationDeletePopupComponent,
    TokenVerificationDeleteDialogComponent,
    tokenVerificationRoute,
    tokenVerificationPopupRoute
} from './';

const ENTITY_STATES = [...tokenVerificationRoute, ...tokenVerificationPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TokenVerificationComponent,
        TokenVerificationDetailComponent,
        TokenVerificationUpdateComponent,
        TokenVerificationDeleteDialogComponent,
        TokenVerificationDeletePopupComponent
    ],
    entryComponents: [
        TokenVerificationComponent,
        TokenVerificationUpdateComponent,
        TokenVerificationDeleteDialogComponent,
        TokenVerificationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5TokenVerificationModule {}
