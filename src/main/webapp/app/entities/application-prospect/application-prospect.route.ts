import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from './application-prospect.service';
import { ApplicationProspectComponent } from './application-prospect.component';
import { ApplicationProspectDetailComponent } from './application-prospect-detail.component';
import { ApplicationProspectUpdateComponent } from './application-prospect-update.component';
import { ApplicationProspectDeletePopupComponent } from './application-prospect-delete-dialog.component';
import { IApplicationProspect } from 'app/shared/model/application-prospect.model';

@Injectable({ providedIn: 'root' })
export class ApplicationProspectResolve implements Resolve<IApplicationProspect> {
    constructor(private service: ApplicationProspectService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((applicationProspect: HttpResponse<ApplicationProspect>) => applicationProspect.body));
        }
        return of(new ApplicationProspect());
    }
}

export const applicationProspectRoute: Routes = [
    {
        path: 'application-prospect',
        component: ApplicationProspectComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'kyc5App.applicationProspect.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application-prospect/:id/view',
        component: ApplicationProspectDetailComponent,
        resolve: {
            applicationProspect: ApplicationProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.applicationProspect.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application-prospect/new',
        component: ApplicationProspectUpdateComponent,
        resolve: {
            applicationProspect: ApplicationProspectResolve
        },
        data: {
            authorities: [],
            pageTitle: 'kyc5App.applicationProspect.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application-prospect/:id/edit',
        component: ApplicationProspectUpdateComponent,
        resolve: {
            applicationProspect: ApplicationProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.applicationProspect.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const applicationProspectPopupRoute: Routes = [
    {
        path: 'application-prospect/:id/delete',
        component: ApplicationProspectDeletePopupComponent,
        resolve: {
            applicationProspect: ApplicationProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.applicationProspect.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
