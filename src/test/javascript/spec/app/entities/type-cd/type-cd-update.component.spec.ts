/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { TypeCdUpdateComponent } from 'app/entities/type-cd/type-cd-update.component';
import { TypeCdService } from 'app/entities/type-cd/type-cd.service';
import { TypeCd } from 'app/shared/model/type-cd.model';

describe('Component Tests', () => {
    describe('TypeCd Management Update Component', () => {
        let comp: TypeCdUpdateComponent;
        let fixture: ComponentFixture<TypeCdUpdateComponent>;
        let service: TypeCdService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TypeCdUpdateComponent]
            })
                .overrideTemplate(TypeCdUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeCdUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCdService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TypeCd(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeCd = entity;
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
                    const entity = new TypeCd();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeCd = entity;
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
