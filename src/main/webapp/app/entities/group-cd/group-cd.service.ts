import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGroupCd } from 'app/shared/model/group-cd.model';

type EntityResponseType = HttpResponse<IGroupCd>;
type EntityArrayResponseType = HttpResponse<IGroupCd[]>;

@Injectable({ providedIn: 'root' })
export class GroupCdService {
    public resourceUrl = SERVER_API_URL + 'api/group-cds';

    constructor(private http: HttpClient) {}

    create(groupCd: IGroupCd): Observable<EntityResponseType> {
        return this.http.post<IGroupCd>(this.resourceUrl, groupCd, { observe: 'response' });
    }

    update(groupCd: IGroupCd): Observable<EntityResponseType> {
        return this.http.put<IGroupCd>(this.resourceUrl, groupCd, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGroupCd>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGroupCd[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
