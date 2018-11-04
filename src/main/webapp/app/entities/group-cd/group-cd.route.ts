import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GroupCd } from 'app/shared/model/group-cd.model';
import { GroupCdService } from './group-cd.service';
import { GroupCdComponent } from './group-cd.component';
import { GroupCdDetailComponent } from './group-cd-detail.component';
import { GroupCdUpdateComponent } from './group-cd-update.component';
import { GroupCdDeletePopupComponent } from './group-cd-delete-dialog.component';
import { IGroupCd } from 'app/shared/model/group-cd.model';

@Injectable({ providedIn: 'root' })
export class GroupCdResolve implements Resolve<IGroupCd> {
    constructor(private service: GroupCdService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((groupCd: HttpResponse<GroupCd>) => groupCd.body));
        }
        return of(new GroupCd());
    }
}

export const groupCdRoute: Routes = [
    {
        path: 'group-cd',
        component: GroupCdComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.groupCd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-cd/:id/view',
        component: GroupCdDetailComponent,
        resolve: {
            groupCd: GroupCdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.groupCd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-cd/new',
        component: GroupCdUpdateComponent,
        resolve: {
            groupCd: GroupCdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.groupCd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-cd/:id/edit',
        component: GroupCdUpdateComponent,
        resolve: {
            groupCd: GroupCdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.groupCd.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groupCdPopupRoute: Routes = [
    {
        path: 'group-cd/:id/delete',
        component: GroupCdDeletePopupComponent,
        resolve: {
            groupCd: GroupCdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.groupCd.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
