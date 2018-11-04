/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { InvestmentPotentialComponent } from 'app/entities/investment-potential/investment-potential.component';
import { InvestmentPotentialService } from 'app/entities/investment-potential/investment-potential.service';
import { InvestmentPotential } from 'app/shared/model/investment-potential.model';

describe('Component Tests', () => {
    describe('InvestmentPotential Management Component', () => {
        let comp: InvestmentPotentialComponent;
        let fixture: ComponentFixture<InvestmentPotentialComponent>;
        let service: InvestmentPotentialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [InvestmentPotentialComponent],
                providers: []
            })
                .overrideTemplate(InvestmentPotentialComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvestmentPotentialComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvestmentPotentialService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new InvestmentPotential(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.investmentPotentials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
