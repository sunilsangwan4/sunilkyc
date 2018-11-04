import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeCd } from 'app/shared/model/type-cd.model';

type EntityResponseType = HttpResponse<ITypeCd>;
type EntityArrayResponseType = HttpResponse<ITypeCd[]>;

@Injectable({ providedIn: 'root' })
export class TypeCdService {
    public resourceUrl = SERVER_API_URL + 'api/type-cds';

    constructor(private http: HttpClient) {}

    create(typeCd: ITypeCd): Observable<EntityResponseType> {
        return this.http.post<ITypeCd>(this.resourceUrl, typeCd, { observe: 'response' });
    }

    update(typeCd: ITypeCd): Observable<EntityResponseType> {
        return this.http.put<ITypeCd>(this.resourceUrl, typeCd, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeCd>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeCd[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
