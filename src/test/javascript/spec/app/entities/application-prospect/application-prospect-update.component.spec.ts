/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { ApplicationProspectUpdateComponent } from 'app/entities/application-prospect/application-prospect-update.component';
import { ApplicationProspectService } from 'app/entities/application-prospect/application-prospect.service';
import { ApplicationProspect } from 'app/shared/model/application-prospect.model';

describe('Component Tests', () => {
    describe('ApplicationProspect Management Update Component', () => {
        let comp: ApplicationProspectUpdateComponent;
        let fixture: ComponentFixture<ApplicationProspectUpdateComponent>;
        let service: ApplicationProspectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [ApplicationProspectUpdateComponent]
            })
                .overrideTemplate(ApplicationProspectUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApplicationProspectUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationProspectService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ApplicationProspect(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.applicationProspect = entity;
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
                    const entity = new ApplicationProspect();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.applicationProspect = entity;
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
