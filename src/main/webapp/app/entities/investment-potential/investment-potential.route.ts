import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InvestmentPotential } from 'app/shared/model/investment-potential.model';
import { InvestmentPotentialService } from './investment-potential.service';
import { InvestmentPotentialComponent } from './investment-potential.component';
import { InvestmentPotentialDetailComponent } from './investment-potential-detail.component';
import { InvestmentPotentialUpdateComponent } from './investment-potential-update.component';
import { InvestmentPotentialDeletePopupComponent } from './investment-potential-delete-dialog.component';
import { IInvestmentPotential } from 'app/shared/model/investment-potential.model';

@Injectable({ providedIn: 'root' })
export class InvestmentPotentialResolve implements Resolve<IInvestmentPotential> {
    constructor(private service: InvestmentPotentialService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((investmentPotential: HttpResponse<InvestmentPotential>) => investmentPotential.body));
        }
        return of(new InvestmentPotential());
    }
}

export const investmentPotentialRoute: Routes = [
    {
        path: 'investment-potential',
        component: InvestmentPotentialComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.investmentPotential.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'investment-potential/:id/view',
        component: InvestmentPotentialDetailComponent,
        resolve: {
            investmentPotential: InvestmentPotentialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.investmentPotential.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'investment-potential/new/:prospectId',
        component: InvestmentPotentialUpdateComponent,
        resolve: {
            investmentPotential: InvestmentPotentialResolve
        },
        data: {
            authorities: [],
            pageTitle: 'kyc5App.investmentPotential.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'investment-potential/:id/edit',
        component: InvestmentPotentialUpdateComponent,
        resolve: {
            investmentPotential: InvestmentPotentialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.investmentPotential.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const investmentPotentialPopupRoute: Routes = [
    {
        path: 'investment-potential/:id/delete',
        component: InvestmentPotentialDeletePopupComponent,
        resolve: {
            investmentPotential: InvestmentPotentialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.investmentPotential.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
