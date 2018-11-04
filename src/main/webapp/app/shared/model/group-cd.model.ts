import { ITypeCd } from 'app/shared/model//type-cd.model';

export interface IGroupCd {
    id?: number;
    groupCd?: string;
    typeCds?: ITypeCd[];
}

export class GroupCd implements IGroupCd {
    constructor(public id?: number, public groupCd?: string, public typeCds?: ITypeCd[]) {}
}
