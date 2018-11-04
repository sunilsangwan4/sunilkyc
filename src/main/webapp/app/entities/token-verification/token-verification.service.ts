import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITokenVerification } from 'app/shared/model/token-verification.model';

type EntityResponseType = HttpResponse<ITokenVerification>;
type EntityArrayResponseType = HttpResponse<ITokenVerification[]>;

@Injectable({ providedIn: 'root' })
export class TokenVerificationService {
    public resourceUrl = SERVER_API_URL + 'api/token-verifications';

    constructor(private http: HttpClient) {}

    create(tokenVerification: ITokenVerification): Observable<EntityResponseType> {
        return this.http.post<ITokenVerification>(this.resourceUrl, tokenVerification, { observe: 'response' });
    }

    update(tokenVerification: ITokenVerification): Observable<EntityResponseType> {
        return this.http.put<ITokenVerification>(this.resourceUrl, tokenVerification, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITokenVerification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITokenVerification[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
