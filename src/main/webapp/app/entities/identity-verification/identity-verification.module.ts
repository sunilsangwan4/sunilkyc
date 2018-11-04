import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    IdentityVerificationComponent,
    IdentityVerificationDetailComponent,
    IdentityVerificationUpdateComponent,
    IdentityVerificationDeletePopupComponent,
    IdentityVerificationDeleteDialogComponent,
    identityVerificationRoute,
    identityVerificationPopupRoute
} from './';

const ENTITY_STATES = [...identityVerificationRoute, ...identityVerificationPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IdentityVerificationComponent,
        IdentityVerificationDetailComponent,
        IdentityVerificationUpdateComponent,
        IdentityVerificationDeleteDialogComponent,
        IdentityVerificationDeletePopupComponent
    ],
    entryComponents: [
        IdentityVerificationComponent,
        IdentityVerificationUpdateComponent,
        IdentityVerificationDeleteDialogComponent,
        IdentityVerificationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5IdentityVerificationModule {}
