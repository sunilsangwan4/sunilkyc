/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Kyc5TestModule } from '../../../test.module';
import { TokenVerificationDeleteDialogComponent } from 'app/entities/token-verification/token-verification-delete-dialog.component';
import { TokenVerificationService } from 'app/entities/token-verification/token-verification.service';

describe('Component Tests', () => {
    describe('TokenVerification Management Delete Component', () => {
        let comp: TokenVerificationDeleteDialogComponent;
        let fixture: ComponentFixture<TokenVerificationDeleteDialogComponent>;
        let service: TokenVerificationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TokenVerificationDeleteDialogComponent]
            })
                .overrideTemplate(TokenVerificationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TokenVerificationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TokenVerificationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
