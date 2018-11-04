/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { BankInformationComponent } from 'app/entities/bank-information/bank-information.component';
import { BankInformationService } from 'app/entities/bank-information/bank-information.service';
import { BankInformation } from 'app/shared/model/bank-information.model';

describe('Component Tests', () => {
    describe('BankInformation Management Component', () => {
        let comp: BankInformationComponent;
        let fixture: ComponentFixture<BankInformationComponent>;
        let service: BankInformationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [BankInformationComponent],
                providers: []
            })
                .overrideTemplate(BankInformationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BankInformationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BankInformationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BankInformation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bankInformations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
