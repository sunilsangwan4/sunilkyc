/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { BankInformationUpdateComponent } from 'app/entities/bank-information/bank-information-update.component';
import { BankInformationService } from 'app/entities/bank-information/bank-information.service';
import { BankInformation } from 'app/shared/model/bank-information.model';

describe('Component Tests', () => {
    describe('BankInformation Management Update Component', () => {
        let comp: BankInformationUpdateComponent;
        let fixture: ComponentFixture<BankInformationUpdateComponent>;
        let service: BankInformationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [BankInformationUpdateComponent]
            })
                .overrideTemplate(BankInformationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BankInformationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BankInformationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BankInformation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bankInformation = entity;
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
                    const entity = new BankInformation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bankInformation = entity;
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
