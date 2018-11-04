import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepositoryInfo } from 'app/shared/model/depository-info.model';

type EntityResponseType = HttpResponse<IDepositoryInfo>;
type EntityArrayResponseType = HttpResponse<IDepositoryInfo[]>;

@Injectable({ providedIn: 'root' })
export class DepositoryInfoService {
    public resourceUrl = SERVER_API_URL + 'api/depository-infos';

    constructor(private http: HttpClient) {}

    create(depositoryInfo: IDepositoryInfo): Observable<EntityResponseType> {
        return this.http.post<IDepositoryInfo>(this.resourceUrl, depositoryInfo, { observe: 'response' });
    }

    update(depositoryInfo: IDepositoryInfo): Observable<EntityResponseType> {
        return this.http.put<IDepositoryInfo>(this.resourceUrl, depositoryInfo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDepositoryInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDepositoryInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
