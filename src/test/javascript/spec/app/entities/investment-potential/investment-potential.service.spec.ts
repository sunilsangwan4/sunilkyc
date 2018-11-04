/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InvestmentPotentialService } from 'app/entities/investment-potential/investment-potential.service';
import { IInvestmentPotential, InvestmentPotential } from 'app/shared/model/investment-potential.model';

describe('Service Tests', () => {
    describe('InvestmentPotential Service', () => {
        let injector: TestBed;
        let service: InvestmentPotentialService;
        let httpMock: HttpTestingController;
        let elemDefault: IInvestmentPotential;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(InvestmentPotentialService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new InvestmentPotential(0, 'AAAAAAA', 'AAAAAAA', 0, 0, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        networthDeclarationDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a InvestmentPotential', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        networthDeclarationDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        networthDeclarationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new InvestmentPotential(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a InvestmentPotential', async () => {
                const returnedFromService = Object.assign(
                    {
                        educationQualification: 'BBBBBB',
                        occupation: 'BBBBBB',
                        annualIncome: 1,
                        netWorth: 1,
                        networthDeclarationDate: currentDate.format(DATE_FORMAT),
                        pepRelative: 'BBBBBB',
                        pmlaRiskCategory: 'BBBBBB',
                        pmlaRiskCategoryReason: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        networthDeclarationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of InvestmentPotential', async () => {
                const returnedFromService = Object.assign(
                    {
                        educationQualification: 'BBBBBB',
                        occupation: 'BBBBBB',
                        annualIncome: 1,
                        netWorth: 1,
                        networthDeclarationDate: currentDate.format(DATE_FORMAT),
                        pepRelative: 'BBBBBB',
                        pmlaRiskCategory: 'BBBBBB',
                        pmlaRiskCategoryReason: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        networthDeclarationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a InvestmentPotential', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
