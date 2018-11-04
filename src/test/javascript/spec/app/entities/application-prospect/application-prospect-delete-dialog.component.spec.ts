/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Kyc5TestModule } from '../../../test.module';
import { ApplicationProspectDeleteDialogComponent } from 'app/entities/application-prospect/application-prospect-delete-dialog.component';
import { ApplicationProspectService } from 'app/entities/application-prospect/application-prospect.service';

describe('Component Tests', () => {
    describe('ApplicationProspect Management Delete Component', () => {
        let comp: ApplicationProspectDeleteDialogComponent;
        let fixture: ComponentFixture<ApplicationProspectDeleteDialogComponent>;
        let service: ApplicationProspectService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [ApplicationProspectDeleteDialogComponent]
            })
                .overrideTemplate(ApplicationProspectDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApplicationProspectDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationProspectService);
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
