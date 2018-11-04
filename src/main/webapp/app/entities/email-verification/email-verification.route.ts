import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EmailVerification } from 'app/shared/model/email-verification.model';
import { EmailVerificationService } from './email-verification.service';
import { EmailVerificationComponent } from './email-verification.component';
import { EmailVerificationDetailComponent } from './email-verification-detail.component';
import { EmailVerificationUpdateComponent } from './email-verification-update.component';
import { EmailVerificationDeletePopupComponent } from './email-verification-delete-dialog.component';
import { IEmailVerification } from 'app/shared/model/email-verification.model';

@Injectable({ providedIn: 'root' })
export class EmailVerificationResolve implements Resolve<IEmailVerification> {
    constructor(private service: EmailVerificationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((emailVerification: HttpResponse<EmailVerification>) => emailVerification.body));
        }
        return of(new EmailVerification());
    }
}

export const emailVerificationRoute: Routes = [
    {
        path: 'email-verification',
        component: EmailVerificationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.emailVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'email-verification/:id/view',
        component: EmailVerificationDetailComponent,
        resolve: {
            emailVerification: EmailVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.emailVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'email-verification/new',
        component: EmailVerificationUpdateComponent,
        resolve: {
            emailVerification: EmailVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.emailVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'email-verification/:id/edit',
        component: EmailVerificationUpdateComponent,
        resolve: {
            emailVerification: EmailVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.emailVerification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emailVerificationPopupRoute: Routes = [
    {
        path: 'email-verification/:id/delete',
        component: EmailVerificationDeletePopupComponent,
        resolve: {
            emailVerification: EmailVerificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.emailVerification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
