import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    NomineeComponent,
    NomineeDetailComponent,
    NomineeUpdateComponent,
    NomineeDeletePopupComponent,
    NomineeDeleteDialogComponent,
    nomineeRoute,
    nomineePopupRoute
} from './';

const ENTITY_STATES = [...nomineeRoute, ...nomineePopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NomineeComponent,
        NomineeDetailComponent,
        NomineeUpdateComponent,
        NomineeDeleteDialogComponent,
        NomineeDeletePopupComponent
    ],
    entryComponents: [NomineeComponent, NomineeUpdateComponent, NomineeDeleteDialogComponent, NomineeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5NomineeModule {}
