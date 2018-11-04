/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Kyc5TestModule } from '../../../test.module';
import { BankInformationDeleteDialogComponent } from 'app/entities/bank-information/bank-information-delete-dialog.component';
import { BankInformationService } from 'app/entities/bank-information/bank-information.service';

describe('Component Tests', () => {
    describe('BankInformation Management Delete Component', () => {
        let comp: BankInformationDeleteDialogComponent;
        let fixture: ComponentFixture<BankInformationDeleteDialogComponent>;
        let service: BankInformationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [BankInformationDeleteDialogComponent]
            })
                .overrideTemplate(BankInformationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BankInformationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BankInformationService);
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
