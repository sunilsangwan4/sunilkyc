/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { TokenVerificationDetailComponent } from 'app/entities/token-verification/token-verification-detail.component';
import { TokenVerification } from 'app/shared/model/token-verification.model';

describe('Component Tests', () => {
    describe('TokenVerification Management Detail Component', () => {
        let comp: TokenVerificationDetailComponent;
        let fixture: ComponentFixture<TokenVerificationDetailComponent>;
        const route = ({ data: of({ tokenVerification: new TokenVerification(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TokenVerificationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TokenVerificationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TokenVerificationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tokenVerification).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
