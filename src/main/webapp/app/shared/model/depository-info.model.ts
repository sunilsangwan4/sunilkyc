export interface IDepositoryInfo {
    id?: number;
    haveAccountWithOtherDP?: boolean;
    haveSMSEnabled?: boolean;
    statementFrequency?: string;
    dpScheme?: string;
    depositoryName?: string;
    brokerName?: string;
    nameAsPerDemat?: string;
    applicationProspectId?: number;
}

export class DepositoryInfo implements IDepositoryInfo {
    constructor(
        public id?: number,
        public haveAccountWithOtherDP?: boolean,
        public haveSMSEnabled?: boolean,
        public statementFrequency?: string,
        public dpScheme?: string,
        public depositoryName?: string,
        public brokerName?: string,
        public nameAsPerDemat?: string,
        public applicationProspectId?: number
    ) {
        this.haveAccountWithOtherDP = this.haveAccountWithOtherDP || false;
        this.haveSMSEnabled = this.haveSMSEnabled || false;
    }
}
