export interface IPersonalInformation {
    id?: number;
    fatherName?: string;
    motherName?: string;
    gender?: string;
    nationality?: string;
    maritalStatus?: string;
    indianTaxPayer?: string;
    birthCountry?: string;
    birthCity?: string;
    jurisdictionCountry?: string;
    taxIdentificationNo?: string;
    applicationProspectId?: number;
}

export class PersonalInformation implements IPersonalInformation {
    constructor(
        public id?: number,
        public fatherName?: string,
        public motherName?: string,
        public gender?: string,
        public nationality?: string,
        public maritalStatus?: string,
        public indianTaxPayer?: string,
        public birthCountry?: string,
        public birthCity?: string,
        public jurisdictionCountry?: string,
        public taxIdentificationNo?: string,
        public applicationProspectId?: number
    ) {}
}
