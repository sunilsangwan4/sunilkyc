import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    TypeCdComponent,
    TypeCdDetailComponent,
    TypeCdUpdateComponent,
    TypeCdDeletePopupComponent,
    TypeCdDeleteDialogComponent,
    typeCdRoute,
    typeCdPopupRoute
} from './';

const ENTITY_STATES = [...typeCdRoute, ...typeCdPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TypeCdComponent, TypeCdDetailComponent, TypeCdUpdateComponent, TypeCdDeleteDialogComponent, TypeCdDeletePopupComponent],
    entryComponents: [TypeCdComponent, TypeCdUpdateComponent, TypeCdDeleteDialogComponent, TypeCdDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5TypeCdModule {}
