import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInvestmentPotential } from 'app/shared/model/investment-potential.model';

type EntityResponseType = HttpResponse<IInvestmentPotential>;
type EntityArrayResponseType = HttpResponse<IInvestmentPotential[]>;

@Injectable({ providedIn: 'root' })
export class InvestmentPotentialService {
    public resourceUrl = SERVER_API_URL + 'api/investment-potentials';

    constructor(private http: HttpClient) {}

    create(investmentPotential: IInvestmentPotential): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(investmentPotential);
        return this.http
            .post<IInvestmentPotential>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(investmentPotential: IInvestmentPotential): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(investmentPotential);
        return this.http
            .put<IInvestmentPotential>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IInvestmentPotential>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IInvestmentPotential[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(investmentPotential: IInvestmentPotential): IInvestmentPotential {
        const copy: IInvestmentPotential = Object.assign({}, investmentPotential, {
            networthDeclarationDate:
                investmentPotential.networthDeclarationDate != null && investmentPotential.networthDeclarationDate.isValid()
                    ? investmentPotential.networthDeclarationDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.networthDeclarationDate = res.body.networthDeclarationDate != null ? moment(res.body.networthDeclarationDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((investmentPotential: IInvestmentPotential) => {
            investmentPotential.networthDeclarationDate =
                investmentPotential.networthDeclarationDate != null ? moment(investmentPotential.networthDeclarationDate) : null;
        });
        return res;
    }
}
