import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PersonalInformation } from 'app/shared/model/personal-information.model';
import { PersonalInformationService } from './personal-information.service';
import { PersonalInformationComponent } from './personal-information.component';
import { PersonalInformationDetailComponent } from './personal-information-detail.component';
import { PersonalInformationUpdateComponent } from './personal-information-update.component';
import { PersonalInformationDeletePopupComponent } from './personal-information-delete-dialog.component';
import { IPersonalInformation } from 'app/shared/model/personal-information.model';

@Injectable({ providedIn: 'root' })
export class PersonalInformationResolve implements Resolve<IPersonalInformation> {
    constructor(private service: PersonalInformationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((personalInformation: HttpResponse<PersonalInformation>) => personalInformation.body));
        }
        return of(new PersonalInformation());
    }
}

export const personalInformationRoute: Routes = [
    {
        path: 'personal-information',
        component: PersonalInformationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.personalInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'personal-information/:id/view',
        component: PersonalInformationDetailComponent,
        resolve: {
            personalInformation: PersonalInformationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.personalInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'personal-information/new/:prospectId',
        component: PersonalInformationUpdateComponent,
        resolve: {
            personalInformation: PersonalInformationResolve
        },
        data: {
            authorities: [],
            pageTitle: 'kyc5App.personalInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'personal-information/:id/edit',
        component: PersonalInformationUpdateComponent,
        resolve: {
            personalInformation: PersonalInformationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.personalInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personalInformationPopupRoute: Routes = [
    {
        path: 'personal-information/:id/delete',
        component: PersonalInformationDeletePopupComponent,
        resolve: {
            personalInformation: PersonalInformationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kyc5App.personalInformation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
