/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { EmailVerificationDetailComponent } from 'app/entities/email-verification/email-verification-detail.component';
import { EmailVerification } from 'app/shared/model/email-verification.model';

describe('Component Tests', () => {
    describe('EmailVerification Management Detail Component', () => {
        let comp: EmailVerificationDetailComponent;
        let fixture: ComponentFixture<EmailVerificationDetailComponent>;
        const route = ({ data: of({ emailVerification: new EmailVerification(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [EmailVerificationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EmailVerificationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmailVerificationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.emailVerification).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
