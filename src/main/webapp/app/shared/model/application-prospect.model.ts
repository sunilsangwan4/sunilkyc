import { IAddress } from 'app/shared/model//address.model';
import { IBankInformation } from 'app/shared/model//bank-information.model';

export interface IApplicationProspect {
    id?: number;
    fullName?: string;
    mobileNo?: string;
    email?: string;
    password?: string;
    confirmPassword?: string;
    personalInformationId?: number;
    investmentPotentialId?: number;
    nomineeId?: number;
    tradingInfoId?: number;
    depositoryId?: number;
    addresses?: IAddress[];
    bankInformations?: IBankInformation[];
}

export class ApplicationProspect implements IApplicationProspect {
    constructor(
        public id?: number,
        public fullName?: string,
        public mobileNo?: string,
        public email?: string,
        public password?: string,
        public confirmPassword?: string,
        public personalInformationId?: number,
        public investmentPotentialId?: number,
        public nomineeId?: number,
        public tradingInfoId?: number,
        public depositoryId?: number,
        public addresses?: IAddress[],
        public bankInformations?: IBankInformation[]
    ) {}
}
