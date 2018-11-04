import { Moment } from 'moment';

export interface IInvestmentPotential {
    id?: number;
    educationQualification?: string;
    occupation?: string;
    annualIncome?: number;
    netWorth?: number;
    networthDeclarationDate?: Moment;
    pepRelative?: string;
    pmlaRiskCategory?: string;
    pmlaRiskCategoryReason?: string;
    applicationProspectId?: number;
}

export class InvestmentPotential implements IInvestmentPotential {
    constructor(
        public id?: number,
        public educationQualification?: string,
        public occupation?: string,
        public annualIncome?: number,
        public netWorth?: number,
        public networthDeclarationDate?: Moment,
        public pepRelative?: string,
        public pmlaRiskCategory?: string,
        public pmlaRiskCategoryReason?: string,
        public applicationProspectId?: number
    ) {}
}
