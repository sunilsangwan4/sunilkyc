/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { InvestmentPotentialUpdateComponent } from 'app/entities/investment-potential/investment-potential-update.component';
import { InvestmentPotentialService } from 'app/entities/investment-potential/investment-potential.service';
import { InvestmentPotential } from 'app/shared/model/investment-potential.model';

describe('Component Tests', () => {
    describe('InvestmentPotential Management Update Component', () => {
        let comp: InvestmentPotentialUpdateComponent;
        let fixture: ComponentFixture<InvestmentPotentialUpdateComponent>;
        let service: InvestmentPotentialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [InvestmentPotentialUpdateComponent]
            })
                .overrideTemplate(InvestmentPotentialUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvestmentPotentialUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvestmentPotentialService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InvestmentPotential(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.investmentPotential = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InvestmentPotential();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.investmentPotential = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
