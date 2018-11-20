import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DepositoryInfo } from 'app/shared/model/depository-info.model';
import { DepositoryInfoService } from './depository-info.service';
import { DepositoryInfoComponent } from './depository-info.component';
import { DepositoryInfoDetailComponent } from './depository-info-detail.component';
import { DepositoryInfoUpdateComponent } from './depository-info-update.component';
import { DepositoryInfoDeletePopupComponent } from './depository-info-delete-dialog.component';
import { IDepositoryInfo } from 'app/shared/model/depository-info.model';

@Injectable({ providedIn: 'root' })
export class DepositoryInfoResolve implements Resolve<IDepositoryInfo> {
    constructor(private service: DepositoryInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((depositoryInfo: HttpResponse<DepositoryInfo>) => depositoryInfo.body));
        }
        return of(new DepositoryInfo());
    }
}

export const depositoryInfoRoute: Routes = [
    {
        path: 'depository-info',
        component: DepositoryInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.depositoryInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'depository-info/:id/view',
        component: DepositoryInfoDetailComponent,
        resolve: {
            depositoryInfo: DepositoryInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.depositoryInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'depository-info/new/:prospectId',
        component: DepositoryInfoUpdateComponent,
        resolve: {
            depositoryInfo: DepositoryInfoResolve
        },
        data: {
            authorities: [],
            pageTitle: 'kyc5App.depositoryInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'depository-info/:id/edit/:prospectId',
        component: DepositoryInfoUpdateComponent,
        resolve: {
            depositoryInfo: DepositoryInfoResolve
        },
        data: {
            authorities: [],
            pageTitle: 'kyc5App.depositoryInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depositoryInfoPopupRoute: Routes = [
    {
        path: 'depository-info/:id/delete',
        component: DepositoryInfoDeletePopupComponent,
        resolve: {
            depositoryInfo: DepositoryInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.depositoryInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
