import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBankInformation } from 'app/shared/model/bank-information.model';

type EntityResponseType = HttpResponse<IBankInformation>;
type EntityArrayResponseType = HttpResponse<IBankInformation[]>;

@Injectable({ providedIn: 'root' })
export class BankInformationService {
    public resourceUrl = SERVER_API_URL + 'api/bank-informations';

    constructor(private http: HttpClient) {}

    create(bankInformation: IBankInformation): Observable<EntityResponseType> {
        return this.http.post<IBankInformation>(this.resourceUrl, bankInformation, { observe: 'response' });
    }

    update(bankInformation: IBankInformation): Observable<EntityResponseType> {
        return this.http.put<IBankInformation>(this.resourceUrl, bankInformation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBankInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBankInformation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
