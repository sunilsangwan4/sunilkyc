/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { BankInformationDetailComponent } from 'app/entities/bank-information/bank-information-detail.component';
import { BankInformation } from 'app/shared/model/bank-information.model';

describe('Component Tests', () => {
    describe('BankInformation Management Detail Component', () => {
        let comp: BankInformationDetailComponent;
        let fixture: ComponentFixture<BankInformationDetailComponent>;
        const route = ({ data: of({ bankInformation: new BankInformation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [BankInformationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BankInformationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BankInformationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bankInformation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
