import { Moment } from 'moment';

export interface IIdentityVerification {
    id?: number;
    adhaarNo?: string;
    aadharNoVerified?: boolean;
    panNo?: string;
    panNoVerified?: boolean;
    dateOfBirth?: Moment;
    applicationProspectId?: number;
}

export class IdentityVerification implements IIdentityVerification {
    constructor(
        public id?: number,
        public adhaarNo?: string,
        public aadharNoVerified?: boolean,
        public panNo?: string,
        public panNoVerified?: boolean,
        public dateOfBirth?: Moment,
        public applicationProspectId?: number
    ) {
        this.aadharNoVerified = this.aadharNoVerified || false;
        this.panNoVerified = this.panNoVerified || false;
    }
}
