import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Nominee } from 'app/shared/model/nominee.model';
import { NomineeService } from './nominee.service';
import { NomineeComponent } from './nominee.component';
import { NomineeDetailComponent } from './nominee-detail.component';
import { NomineeUpdateComponent } from './nominee-update.component';
import { NomineeDeletePopupComponent } from './nominee-delete-dialog.component';
import { INominee } from 'app/shared/model/nominee.model';

@Injectable({ providedIn: 'root' })
export class NomineeResolve implements Resolve<INominee> {
    constructor(private service: NomineeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((nominee: HttpResponse<Nominee>) => nominee.body));
        }
        return of(new Nominee());
    }
}

export const nomineeRoute: Routes = [
    {
        path: 'nominee',
        component: NomineeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.nominee.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nominee/:id/view',
        component: NomineeDetailComponent,
        resolve: {
            nominee: NomineeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.nominee.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nominee/new',
        component: NomineeUpdateComponent,
        resolve: {
            nominee: NomineeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.nominee.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nominee/:id/edit',
        component: NomineeUpdateComponent,
        resolve: {
            nominee: NomineeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.nominee.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nomineePopupRoute: Routes = [
    {
        path: 'nominee/:id/delete',
        component: NomineeDeletePopupComponent,
        resolve: {
            nominee: NomineeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.nominee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
