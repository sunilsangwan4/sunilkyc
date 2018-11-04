import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    PersonalInformationComponent,
    PersonalInformationDetailComponent,
    PersonalInformationUpdateComponent,
    PersonalInformationDeletePopupComponent,
    PersonalInformationDeleteDialogComponent,
    personalInformationRoute,
    personalInformationPopupRoute
} from './';

const ENTITY_STATES = [...personalInformationRoute, ...personalInformationPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PersonalInformationComponent,
        PersonalInformationDetailComponent,
        PersonalInformationUpdateComponent,
        PersonalInformationDeleteDialogComponent,
        PersonalInformationDeletePopupComponent
    ],
    entryComponents: [
        PersonalInformationComponent,
        PersonalInformationUpdateComponent,
        PersonalInformationDeleteDialogComponent,
        PersonalInformationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5PersonalInformationModule {}
