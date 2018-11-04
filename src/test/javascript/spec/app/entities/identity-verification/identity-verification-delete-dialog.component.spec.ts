/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Kyc5TestModule } from '../../../test.module';
import { IdentityVerificationDeleteDialogComponent } from 'app/entities/identity-verification/identity-verification-delete-dialog.component';
import { IdentityVerificationService } from 'app/entities/identity-verification/identity-verification.service';

describe('Component Tests', () => {
    describe('IdentityVerification Management Delete Component', () => {
        let comp: IdentityVerificationDeleteDialogComponent;
        let fixture: ComponentFixture<IdentityVerificationDeleteDialogComponent>;
        let service: IdentityVerificationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [IdentityVerificationDeleteDialogComponent]
            })
                .overrideTemplate(IdentityVerificationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IdentityVerificationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IdentityVerificationService);
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
