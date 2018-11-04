import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INominee } from 'app/shared/model/nominee.model';

type EntityResponseType = HttpResponse<INominee>;
type EntityArrayResponseType = HttpResponse<INominee[]>;

@Injectable({ providedIn: 'root' })
export class NomineeService {
    public resourceUrl = SERVER_API_URL + 'api/nominees';

    constructor(private http: HttpClient) {}

    create(nominee: INominee): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(nominee);
        return this.http
            .post<INominee>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(nominee: INominee): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(nominee);
        return this.http
            .put<INominee>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<INominee>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<INominee[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(nominee: INominee): INominee {
        const copy: INominee = Object.assign({}, nominee, {
            dateOfBirth: nominee.dateOfBirth != null && nominee.dateOfBirth.isValid() ? nominee.dateOfBirth.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateOfBirth = res.body.dateOfBirth != null ? moment(res.body.dateOfBirth) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((nominee: INominee) => {
            nominee.dateOfBirth = nominee.dateOfBirth != null ? moment(nominee.dateOfBirth) : null;
        });
        return res;
    }
}
