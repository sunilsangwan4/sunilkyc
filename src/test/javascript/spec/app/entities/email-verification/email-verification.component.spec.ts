/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { EmailVerificationComponent } from 'app/entities/email-verification/email-verification.component';
import { EmailVerificationService } from 'app/entities/email-verification/email-verification.service';
import { EmailVerification } from 'app/shared/model/email-verification.model';

describe('Component Tests', () => {
    describe('EmailVerification Management Component', () => {
        let comp: EmailVerificationComponent;
        let fixture: ComponentFixture<EmailVerificationComponent>;
        let service: EmailVerificationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [EmailVerificationComponent],
                providers: []
            })
                .overrideTemplate(EmailVerificationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmailVerificationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailVerificationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EmailVerification(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.emailVerifications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
