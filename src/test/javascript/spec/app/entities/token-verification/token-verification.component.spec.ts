/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { TokenVerificationComponent } from 'app/entities/token-verification/token-verification.component';
import { TokenVerificationService } from 'app/entities/token-verification/token-verification.service';
import { TokenVerification } from 'app/shared/model/token-verification.model';

describe('Component Tests', () => {
    describe('TokenVerification Management Component', () => {
        let comp: TokenVerificationComponent;
        let fixture: ComponentFixture<TokenVerificationComponent>;
        let service: TokenVerificationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TokenVerificationComponent],
                providers: []
            })
                .overrideTemplate(TokenVerificationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TokenVerificationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TokenVerificationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TokenVerification(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tokenVerifications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
