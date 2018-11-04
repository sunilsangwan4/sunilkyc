import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Kyc5SharedModule } from 'app/shared';
import {
    TradingInfoComponent,
    TradingInfoDetailComponent,
    TradingInfoUpdateComponent,
    TradingInfoDeletePopupComponent,
    TradingInfoDeleteDialogComponent,
    tradingInfoRoute,
    tradingInfoPopupRoute
} from './';

const ENTITY_STATES = [...tradingInfoRoute, ...tradingInfoPopupRoute];

@NgModule({
    imports: [Kyc5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TradingInfoComponent,
        TradingInfoDetailComponent,
        TradingInfoUpdateComponent,
        TradingInfoDeleteDialogComponent,
        TradingInfoDeletePopupComponent
    ],
    entryComponents: [TradingInfoComponent, TradingInfoUpdateComponent, TradingInfoDeleteDialogComponent, TradingInfoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5TradingInfoModule {}
