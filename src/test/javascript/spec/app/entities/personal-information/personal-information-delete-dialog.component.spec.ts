/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Kyc5TestModule } from '../../../test.module';
import { PersonalInformationDeleteDialogComponent } from 'app/entities/personal-information/personal-information-delete-dialog.component';
import { PersonalInformationService } from 'app/entities/personal-information/personal-information.service';

describe('Component Tests', () => {
    describe('PersonalInformation Management Delete Component', () => {
        let comp: PersonalInformationDeleteDialogComponent;
        let fixture: ComponentFixture<PersonalInformationDeleteDialogComponent>;
        let service: PersonalInformationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [PersonalInformationDeleteDialogComponent]
            })
                .overrideTemplate(PersonalInformationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonalInformationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonalInformationService);
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
