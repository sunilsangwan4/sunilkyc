import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BankInformation } from 'app/shared/model/bank-information.model';
import { BankInformationService } from './bank-information.service';
import { BankInformationComponent } from './bank-information.component';
import { BankInformationDetailComponent } from './bank-information-detail.component';
import { BankInformationUpdateComponent } from './bank-information-update.component';
import { BankInformationDeletePopupComponent } from './bank-information-delete-dialog.component';
import { IBankInformation } from 'app/shared/model/bank-information.model';

@Injectable({ providedIn: 'root' })
export class BankInformationResolve implements Resolve<IBankInformation> {
    constructor(private service: BankInformationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bankInformation: HttpResponse<BankInformation>) => bankInformation.body));
        }
        return of(new BankInformation());
    }
}

export const bankInformationRoute: Routes = [
    {
        path: 'bank-information',
        component: BankInformationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.bankInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bank-information/:id/view',
        component: BankInformationDetailComponent,
        resolve: {
            bankInformation: BankInformationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.bankInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bank-information/new/:prospectId',
        component: BankInformationUpdateComponent,
        resolve: {
            bankInformation: BankInformationResolve
        },
        data: {
            authorities: [],
            pageTitle: 'kyc5App.bankInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bank-information/:id/edit/:prospectId',
        component: BankInformationUpdateComponent,
        resolve: {
            bankInformation: BankInformationResolve
        },
        data: {
            authorities: [],
            pageTitle: 'kyc5App.bankInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bankInformationPopupRoute: Routes = [
    {
        path: 'bank-information/:id/delete',
        component: BankInformationDeletePopupComponent,
        resolve: {
            bankInformation: BankInformationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.bankInformation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
