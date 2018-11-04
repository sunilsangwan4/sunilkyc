import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TypeCd } from 'app/shared/model/type-cd.model';
import { TypeCdService } from './type-cd.service';
import { TypeCdComponent } from './type-cd.component';
import { TypeCdDetailComponent } from './type-cd-detail.component';
import { TypeCdUpdateComponent } from './type-cd-update.component';
import { TypeCdDeletePopupComponent } from './type-cd-delete-dialog.component';
import { ITypeCd } from 'app/shared/model/type-cd.model';

@Injectable({ providedIn: 'root' })
export class TypeCdResolve implements Resolve<ITypeCd> {
    constructor(private service: TypeCdService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((typeCd: HttpResponse<TypeCd>) => typeCd.body));
        }
        return of(new TypeCd());
    }
}

export const typeCdRoute: Routes = [
    {
        path: 'type-cd',
        component: TypeCdComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.typeCd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-cd/:id/view',
        component: TypeCdDetailComponent,
        resolve: {
            typeCd: TypeCdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.typeCd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-cd/new',
        component: TypeCdUpdateComponent,
        resolve: {
            typeCd: TypeCdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.typeCd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-cd/:id/edit',
        component: TypeCdUpdateComponent,
        resolve: {
            typeCd: TypeCdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.typeCd.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeCdPopupRoute: Routes = [
    {
        path: 'type-cd/:id/delete',
        component: TypeCdDeletePopupComponent,
        resolve: {
            typeCd: TypeCdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.typeCd.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
