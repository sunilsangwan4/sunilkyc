/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { NomineeUpdateComponent } from 'app/entities/nominee/nominee-update.component';
import { NomineeService } from 'app/entities/nominee/nominee.service';
import { Nominee } from 'app/shared/model/nominee.model';

describe('Component Tests', () => {
    describe('Nominee Management Update Component', () => {
        let comp: NomineeUpdateComponent;
        let fixture: ComponentFixture<NomineeUpdateComponent>;
        let service: NomineeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [NomineeUpdateComponent]
            })
                .overrideTemplate(NomineeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NomineeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NomineeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Nominee(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.nominee = entity;
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
                    const entity = new Nominee();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.nominee = entity;
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
