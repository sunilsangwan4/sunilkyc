/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IdentityVerificationService } from 'app/entities/identity-verification/identity-verification.service';
import { IIdentityVerification, IdentityVerification } from 'app/shared/model/identity-verification.model';

describe('Service Tests', () => {
    describe('IdentityVerification Service', () => {
        let injector: TestBed;
        let service: IdentityVerificationService;
        let httpMock: HttpTestingController;
        let elemDefault: IIdentityVerification;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(IdentityVerificationService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new IdentityVerification(0, 'AAAAAAA', false, 'AAAAAAA', false, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateOfBirth: currentDate.format(DATE_FORMAT)
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

            it('should create a IdentityVerification', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateOfBirth: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfBirth: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new IdentityVerification(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a IdentityVerification', async () => {
                const returnedFromService = Object.assign(
                    {
                        adhaarNo: 'BBBBBB',
                        aadharNoVerified: true,
                        panNo: 'BBBBBB',
                        panNoVerified: true,
                        dateOfBirth: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateOfBirth: currentDate
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

            it('should return a list of IdentityVerification', async () => {
                const returnedFromService = Object.assign(
                    {
                        adhaarNo: 'BBBBBB',
                        aadharNoVerified: true,
                        panNo: 'BBBBBB',
                        panNoVerified: true,
                        dateOfBirth: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfBirth: currentDate
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

            it('should delete a IdentityVerification', async () => {
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
