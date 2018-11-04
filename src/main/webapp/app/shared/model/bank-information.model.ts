export interface IBankInformation {
    id?: number;
    bankName?: string;
    ifscCode?: string;
    micrCode?: string;
    branchName?: string;
    accountType?: string;
    accountNumber?: string;
    accountHolderName?: string;
    bankAccountCommon?: boolean;
    segementTypeCd?: string;
    applicationProspectId?: number;
}

export class BankInformation implements IBankInformation {
    constructor(
        public id?: number,
        public bankName?: string,
        public ifscCode?: string,
        public micrCode?: string,
        public branchName?: string,
        public accountType?: string,
        public accountNumber?: string,
        public accountHolderName?: string,
        public bankAccountCommon?: boolean,
        public segementTypeCd?: string,
        public applicationProspectId?: number
    ) {
        this.bankAccountCommon = this.bankAccountCommon || false;
    }
}
