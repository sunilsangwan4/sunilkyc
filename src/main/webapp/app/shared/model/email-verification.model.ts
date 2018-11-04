export interface IEmailVerification {
    id?: number;
    emailId?: string;
    token?: string;
    status?: string;
}

export class EmailVerification implements IEmailVerification {
    constructor(public id?: number, public emailId?: string, public token?: string, public status?: string) {}
}
