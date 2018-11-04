import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TradingInfo } from 'app/shared/model/trading-info.model';
import { TradingInfoService } from './trading-info.service';
import { TradingInfoComponent } from './trading-info.component';
import { TradingInfoDetailComponent } from './trading-info-detail.component';
import { TradingInfoUpdateComponent } from './trading-info-update.component';
import { TradingInfoDeletePopupComponent } from './trading-info-delete-dialog.component';
import { ITradingInfo } from 'app/shared/model/trading-info.model';

@Injectable({ providedIn: 'root' })
export class TradingInfoResolve implements Resolve<ITradingInfo> {
    constructor(private service: TradingInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((tradingInfo: HttpResponse<TradingInfo>) => tradingInfo.body));
        }
        return of(new TradingInfo());
    }
}

export const tradingInfoRoute: Routes = [
    {
        path: 'trading-info',
        component: TradingInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tradingInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'trading-info/:id/view',
        component: TradingInfoDetailComponent,
        resolve: {
            tradingInfo: TradingInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tradingInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'trading-info/new',
        component: TradingInfoUpdateComponent,
        resolve: {
            tradingInfo: TradingInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tradingInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'trading-info/:id/edit',
        component: TradingInfoUpdateComponent,
        resolve: {
            tradingInfo: TradingInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tradingInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tradingInfoPopupRoute: Routes = [
    {
        path: 'trading-info/:id/delete',
        component: TradingInfoDeletePopupComponent,
        resolve: {
            tradingInfo: TradingInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tradingInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
