import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    ApplicationProspectComponent,
    ApplicationProspectDetailComponent,
    ApplicationProspectUpdateComponent,
    ApplicationProspectDeletePopupComponent,
    ApplicationProspectDeleteDialogComponent,
    applicationProspectRoute,
    applicationProspectPopupRoute
} from './';

const ENTITY_STATES = [...applicationProspectRoute, ...applicationProspectPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApplicationProspectComponent,
        ApplicationProspectDetailComponent,
        ApplicationProspectUpdateComponent,
        ApplicationProspectDeleteDialogComponent,
        ApplicationProspectDeletePopupComponent
    ],
    entryComponents: [
        ApplicationProspectComponent,
        ApplicationProspectUpdateComponent,
        ApplicationProspectDeleteDialogComponent,
        ApplicationProspectDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5ApplicationProspectModule {}
