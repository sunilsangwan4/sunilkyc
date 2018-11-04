import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    EmailVerificationComponent,
    EmailVerificationDetailComponent,
    EmailVerificationUpdateComponent,
    EmailVerificationDeletePopupComponent,
    EmailVerificationDeleteDialogComponent,
    emailVerificationRoute,
    emailVerificationPopupRoute
} from './';

const ENTITY_STATES = [...emailVerificationRoute, ...emailVerificationPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmailVerificationComponent,
        EmailVerificationDetailComponent,
        EmailVerificationUpdateComponent,
        EmailVerificationDeleteDialogComponent,
        EmailVerificationDeletePopupComponent
    ],
    entryComponents: [
        EmailVerificationComponent,
        EmailVerificationUpdateComponent,
        EmailVerificationDeleteDialogComponent,
        EmailVerificationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5EmailVerificationModule {}
