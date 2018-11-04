/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { IdentityVerificationComponent } from 'app/entities/identity-verification/identity-verification.component';
import { IdentityVerificationService } from 'app/entities/identity-verification/identity-verification.service';
import { IdentityVerification } from 'app/shared/model/identity-verification.model';

describe('Component Tests', () => {
    describe('IdentityVerification Management Component', () => {
        let comp: IdentityVerificationComponent;
        let fixture: ComponentFixture<IdentityVerificationComponent>;
        let service: IdentityVerificationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [IdentityVerificationComponent],
                providers: []
            })
                .overrideTemplate(IdentityVerificationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IdentityVerificationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IdentityVerificationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new IdentityVerification(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.identityVerifications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
