import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonalInformation } from 'app/shared/model/personal-information.model';

type EntityResponseType = HttpResponse<IPersonalInformation>;
type EntityArrayResponseType = HttpResponse<IPersonalInformation[]>;

@Injectable({ providedIn: 'root' })
export class PersonalInformationService {
    public resourceUrl = SERVER_API_URL + 'api/personal-informations';

    constructor(private http: HttpClient) {}

    create(personalInformation: IPersonalInformation): Observable<EntityResponseType> {
        return this.http.post<IPersonalInformation>(this.resourceUrl, personalInformation, { observe: 'response' });
    }

    update(personalInformation: IPersonalInformation): Observable<EntityResponseType> {
        return this.http.put<IPersonalInformation>(this.resourceUrl, personalInformation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPersonalInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPersonalInformation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
