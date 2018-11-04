import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIdentityVerification } from 'app/shared/model/identity-verification.model';

type EntityResponseType = HttpResponse<IIdentityVerification>;
type EntityArrayResponseType = HttpResponse<IIdentityVerification[]>;

@Injectable({ providedIn: 'root' })
export class IdentityVerificationService {
    public resourceUrl = SERVER_API_URL + 'api/identity-verifications';

    constructor(private http: HttpClient) {}

    create(identityVerification: IIdentityVerification): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(identityVerification);
        return this.http
            .post<IIdentityVerification>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(identityVerification: IIdentityVerification): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(identityVerification);
        return this.http
            .put<IIdentityVerification>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IIdentityVerification>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IIdentityVerification[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(identityVerification: IIdentityVerification): IIdentityVerification {
        const copy: IIdentityVerification = Object.assign({}, identityVerification, {
            dateOfBirth:
                identityVerification.dateOfBirth != null && identityVerification.dateOfBirth.isValid()
                    ? identityVerification.dateOfBirth.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateOfBirth = res.body.dateOfBirth != null ? moment(res.body.dateOfBirth) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((identityVerification: IIdentityVerification) => {
            identityVerification.dateOfBirth = identityVerification.dateOfBirth != null ? moment(identityVerification.dateOfBirth) : null;
        });
        return res;
    }
}
