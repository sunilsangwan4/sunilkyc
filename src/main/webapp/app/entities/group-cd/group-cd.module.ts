import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    GroupCdComponent,
    GroupCdDetailComponent,
    GroupCdUpdateComponent,
    GroupCdDeletePopupComponent,
    GroupCdDeleteDialogComponent,
    groupCdRoute,
    groupCdPopupRoute
} from './';

const ENTITY_STATES = [...groupCdRoute, ...groupCdPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GroupCdComponent,
        GroupCdDetailComponent,
        GroupCdUpdateComponent,
        GroupCdDeleteDialogComponent,
        GroupCdDeletePopupComponent
    ],
    entryComponents: [GroupCdComponent, GroupCdUpdateComponent, GroupCdDeleteDialogComponent, GroupCdDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5GroupCdModule {}
