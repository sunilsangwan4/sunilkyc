export interface ITypeCd {
    id?: number;
    typeCd?: string;
    typeDescription?: string;
    groupCdId?: number;
}

export class TypeCd implements ITypeCd {
    constructor(public id?: number, public typeCd?: string, public typeDescription?: string, public groupCdId?: number) {}
}
