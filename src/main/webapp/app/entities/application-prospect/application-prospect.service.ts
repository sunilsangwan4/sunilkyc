import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApplicationProspect } from 'app/shared/model/application-prospect.model';

type EntityResponseType = HttpResponse<IApplicationProspect>;
type EntityArrayResponseType = HttpResponse<IApplicationProspect[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationProspectService {
    public resourceUrl = SERVER_API_URL + 'api/application-prospects';

    constructor(private http: HttpClient) {}

    create(applicationProspect: IApplicationProspect): Observable<EntityResponseType> {
        return this.http.post<IApplicationProspect>(this.resourceUrl, applicationProspect, { observe: 'response' });
    }

    update(applicationProspect: IApplicationProspect): Observable<EntityResponseType> {
        return this.http.put<IApplicationProspect>(this.resourceUrl, applicationProspect, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IApplicationProspect>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IApplicationProspect[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
