/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { TokenVerificationUpdateComponent } from 'app/entities/token-verification/token-verification-update.component';
import { TokenVerificationService } from 'app/entities/token-verification/token-verification.service';
import { TokenVerification } from 'app/shared/model/token-verification.model';

describe('Component Tests', () => {
    describe('TokenVerification Management Update Component', () => {
        let comp: TokenVerificationUpdateComponent;
        let fixture: ComponentFixture<TokenVerificationUpdateComponent>;
        let service: TokenVerificationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TokenVerificationUpdateComponent]
            })
                .overrideTemplate(TokenVerificationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TokenVerificationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TokenVerificationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TokenVerification(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tokenVerification = entity;
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
                    const entity = new TokenVerification();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tokenVerification = entity;
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
