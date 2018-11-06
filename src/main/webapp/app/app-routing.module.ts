import { NgModule } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                ...LAYOUT_ROUTES,
                {
                    path: '',
                    redirectTo: '/application-prospect/new',
                    pathMatch: 'full'
                },
                {
                    path: 'admin',
                    loadChildren: './admin/admin.module#Kyc5AdminModule'
                }
            ],
            { useHash: true, enableTracing: DEBUG_INFO_ENABLED }
        )
    ],
    exports: [RouterModule, Router]
})
export class Kyc5AppRoutingModule {}
