import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IdentityVerification } from 'app/shared/model/identity-verification.model';
import { IdentityVerificationService } from './identity-verification.service';
import { IdentityVerificationComponent } from './identity-verification.component';
import { IdentityVerificationDetailComponent } from './identity-verification-detail.component';
import { IdentityVerificationUpdateComponent } from './identity-verification-update.component';
import { IdentityVerificationDeletePopupComponent } from './identity-verification-delete-dialog.component';
import { IIdentityVerification } from 'app/shared/model/identity-verification.model';

@Injectable({ providedIn: 'root' })
export class IdentityVerificationResolve implements Resolve<IIdentityVerification> {
    constructor(private service: IdentityVerificationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((identityVerification: HttpResponse<IdentityVerification>) => identityVerification.body));
        }
        return of(new IdentityVerification());
    }
}

export const identityVerificationRoute: Routes = [
    {
        path: 'identity-verification',
        component: IdentityVerificationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.identityVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'identity-verification/:id/view',
        component: IdentityVerificationDetailComponent,
        resolve: {
            identityVerification: IdentityVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.identityVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'identity-verification/new/:prospectId',
        component: IdentityVerificationUpdateComponent,
        resolve: {
            identityVerification: IdentityVerificationResolve
        },
        data: {
            authorities: [],
            pageTitle: 'kyc5App.identityVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'identity-verification/:id/edit',
        component: IdentityVerificationUpdateComponent,
        resolve: {
            identityVerification: IdentityVerificationResolve
        },
        data: {
            authorities: [],
            pageTitle: 'kyc5App.identityVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const identityVerificationPopupRoute: Routes = [
    {
        path: 'identity-verification/:id/delete',
        component: IdentityVerificationDeletePopupComponent,
        resolve: {
            identityVerification: IdentityVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.identityVerification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
