import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Kyc5GroupCdModule } from './group-cd/group-cd.module';
import { Kyc5TypeCdModule } from './type-cd/type-cd.module';
import { Kyc5TokenVerificationModule } from './token-verification/token-verification.module';
import { Kyc5EmailVerificationModule } from './email-verification/email-verification.module';
import { Kyc5ApplicationProspectModule } from './application-prospect/application-prospect.module';
import { Kyc5IdentityVerificationModule } from './identity-verification/identity-verification.module';
import { Kyc5AddressModule } from './address/address.module';
import { Kyc5BankInformationModule } from './bank-information/bank-information.module';
import { Kyc5PersonalInformationModule } from './personal-information/personal-information.module';
import { Kyc5InvestmentPotentialModule } from './investment-potential/investment-potential.module';
import { Kyc5NomineeModule } from './nominee/nominee.module';
import { Kyc5TradingInfoModule } from './trading-info/trading-info.module';
import { Kyc5DepositoryInfoModule } from './depository-info/depository-info.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        Kyc5GroupCdModule,
        Kyc5TypeCdModule,
        Kyc5TokenVerificationModule,
        Kyc5EmailVerificationModule,
        Kyc5ApplicationProspectModule,
        Kyc5IdentityVerificationModule,
        Kyc5AddressModule,
        Kyc5BankInformationModule,
        Kyc5PersonalInformationModule,
        Kyc5InvestmentPotentialModule,
        Kyc5NomineeModule,
        Kyc5TradingInfoModule,
        Kyc5DepositoryInfoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Kyc5EntityModule {}
