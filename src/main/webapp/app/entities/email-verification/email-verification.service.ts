import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmailVerification } from 'app/shared/model/email-verification.model';

type EntityResponseType = HttpResponse<IEmailVerification>;
type EntityArrayResponseType = HttpResponse<IEmailVerification[]>;

@Injectable({ providedIn: 'root' })
export class EmailVerificationService {
    public resourceUrl = SERVER_API_URL + 'api/email-verifications';

    constructor(private http: HttpClient) {}

    create(emailVerification: IEmailVerification): Observable<EntityResponseType> {
        return this.http.post<IEmailVerification>(this.resourceUrl, emailVerification, { observe: 'response' });
    }

    update(emailVerification: IEmailVerification): Observable<EntityResponseType> {
        return this.http.put<IEmailVerification>(this.resourceUrl, emailVerification, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEmailVerification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEmailVerification[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
