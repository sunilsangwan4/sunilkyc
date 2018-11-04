/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { IdentityVerificationDetailComponent } from 'app/entities/identity-verification/identity-verification-detail.component';
import { IdentityVerification } from 'app/shared/model/identity-verification.model';

describe('Component Tests', () => {
    describe('IdentityVerification Management Detail Component', () => {
        let comp: IdentityVerificationDetailComponent;
        let fixture: ComponentFixture<IdentityVerificationDetailComponent>;
        const route = ({ data: of({ identityVerification: new IdentityVerification(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [IdentityVerificationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IdentityVerificationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IdentityVerificationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.identityVerification).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
