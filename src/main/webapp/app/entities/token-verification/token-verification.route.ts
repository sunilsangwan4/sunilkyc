import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TokenVerification } from 'app/shared/model/token-verification.model';
import { TokenVerificationService } from './token-verification.service';
import { TokenVerificationComponent } from './token-verification.component';
import { TokenVerificationDetailComponent } from './token-verification-detail.component';
import { TokenVerificationUpdateComponent } from './token-verification-update.component';
import { TokenVerificationDeletePopupComponent } from './token-verification-delete-dialog.component';
import { ITokenVerification } from 'app/shared/model/token-verification.model';

@Injectable({ providedIn: 'root' })
export class TokenVerificationResolve implements Resolve<ITokenVerification> {
    constructor(private service: TokenVerificationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((tokenVerification: HttpResponse<TokenVerification>) => tokenVerification.body));
        }
        return of(new TokenVerification());
    }
}

export const tokenVerificationRoute: Routes = [
    {
        path: 'token-verification',
        component: TokenVerificationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tokenVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'token-verification/:id/view',
        component: TokenVerificationDetailComponent,
        resolve: {
            tokenVerification: TokenVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tokenVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'token-verification/new',
        component: TokenVerificationUpdateComponent,
        resolve: {
            tokenVerification: TokenVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tokenVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'token-verification/:id/edit',
        component: TokenVerificationUpdateComponent,
        resolve: {
            tokenVerification: TokenVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tokenVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tokenVerificationPopupRoute: Routes = [
    {
        path: 'token-verification/:id/delete',
        component: TokenVerificationDeletePopupComponent,
        resolve: {
            tokenVerification: TokenVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.tokenVerification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
