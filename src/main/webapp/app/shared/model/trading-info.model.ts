export interface ITradingInfo {
    id?: number;
    segmentCd?: string;
    planCdEquity?: string;
    planCdCommodity?: string;
    contractNoteMode?: string;
    tradingMode?: string;
    interestedInMobileTradeing?: boolean;
    accountAuthFrequency?: string;
    experienceYear?: number;
    experienceMonth?: number;
    applicationProspectId?: number;
}

export class TradingInfo implements ITradingInfo {
    constructor(
        public id?: number,
        public segmentCd?: string,
        public planCdEquity?: string,
        public planCdCommodity?: string,
        public contractNoteMode?: string,
        public tradingMode?: string,
        public interestedInMobileTradeing?: boolean,
        public accountAuthFrequency?: string,
        public experienceYear?: number,
        public experienceMonth?: number,
        public applicationProspectId?: number
    ) {
        this.interestedInMobileTradeing = this.interestedInMobileTradeing || false;
    }
}
