export interface IAddress {
    id?: number;
    addressLine1?: string;
    addressLine2?: string;
    addressLine3?: string;
    state?: string;
    city?: string;
    pinCode?: string;
    country?: string;
    addressType?: string;
    applicationProspectId?: number;
    nomineeId?: number;
}

export class Address implements IAddress {
    constructor(
        public id?: number,
        public addressLine1?: string,
        public addressLine2?: string,
        public addressLine3?: string,
        public state?: string,
        public city?: string,
        public pinCode?: string,
        public country?: string,
        public addressType?: string,
        public applicationProspectId?: number,
        public nomineeId?: number
    ) {}
}
