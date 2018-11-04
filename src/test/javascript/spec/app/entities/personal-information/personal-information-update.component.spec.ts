/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { PersonalInformationUpdateComponent } from 'app/entities/personal-information/personal-information-update.component';
import { PersonalInformationService } from 'app/entities/personal-information/personal-information.service';
import { PersonalInformation } from 'app/shared/model/personal-information.model';

describe('Component Tests', () => {
    describe('PersonalInformation Management Update Component', () => {
        let comp: PersonalInformationUpdateComponent;
        let fixture: ComponentFixture<PersonalInformationUpdateComponent>;
        let service: PersonalInformationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [PersonalInformationUpdateComponent]
            })
                .overrideTemplate(PersonalInformationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonalInformationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonalInformationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PersonalInformation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personalInformation = entity;
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
                    const entity = new PersonalInformation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personalInformation = entity;
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
