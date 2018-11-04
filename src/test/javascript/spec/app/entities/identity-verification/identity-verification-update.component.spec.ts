/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { IdentityVerificationUpdateComponent } from 'app/entities/identity-verification/identity-verification-update.component';
import { IdentityVerificationService } from 'app/entities/identity-verification/identity-verification.service';
import { IdentityVerification } from 'app/shared/model/identity-verification.model';

describe('Component Tests', () => {
    describe('IdentityVerification Management Update Component', () => {
        let comp: IdentityVerificationUpdateComponent;
        let fixture: ComponentFixture<IdentityVerificationUpdateComponent>;
        let service: IdentityVerificationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [IdentityVerificationUpdateComponent]
            })
                .overrideTemplate(IdentityVerificationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IdentityVerificationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IdentityVerificationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IdentityVerification(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.identityVerification = entity;
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
                    const entity = new IdentityVerification();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.identityVerification = entity;
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
