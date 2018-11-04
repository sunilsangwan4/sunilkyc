/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { EmailVerificationUpdateComponent } from 'app/entities/email-verification/email-verification-update.component';
import { EmailVerificationService } from 'app/entities/email-verification/email-verification.service';
import { EmailVerification } from 'app/shared/model/email-verification.model';

describe('Component Tests', () => {
    describe('EmailVerification Management Update Component', () => {
        let comp: EmailVerificationUpdateComponent;
        let fixture: ComponentFixture<EmailVerificationUpdateComponent>;
        let service: EmailVerificationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [EmailVerificationUpdateComponent]
            })
                .overrideTemplate(EmailVerificationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmailVerificationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailVerificationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EmailVerification(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.emailVerification = entity;
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
                    const entity = new EmailVerification();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.emailVerification = entity;
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
