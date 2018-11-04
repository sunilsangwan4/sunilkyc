export interface ITokenVerification {
    id?: number;
    mobileNo?: string;
    token?: string;
    status?: string;
}

export class TokenVerification implements ITokenVerification {
    constructor(public id?: number, public mobileNo?: string, public token?: string, public status?: string) {}
}
