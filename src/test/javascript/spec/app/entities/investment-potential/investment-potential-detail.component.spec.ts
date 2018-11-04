/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { InvestmentPotentialDetailComponent } from 'app/entities/investment-potential/investment-potential-detail.component';
import { InvestmentPotential } from 'app/shared/model/investment-potential.model';

describe('Component Tests', () => {
    describe('InvestmentPotential Management Detail Component', () => {
        let comp: InvestmentPotentialDetailComponent;
        let fixture: ComponentFixture<InvestmentPotentialDetailComponent>;
        const route = ({ data: of({ investmentPotential: new InvestmentPotential(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [InvestmentPotentialDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InvestmentPotentialDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvestmentPotentialDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.investmentPotential).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
