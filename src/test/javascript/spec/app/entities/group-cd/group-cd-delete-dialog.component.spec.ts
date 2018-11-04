/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Kyc5TestModule } from '../../../test.module';
import { GroupCdDeleteDialogComponent } from 'app/entities/group-cd/group-cd-delete-dialog.component';
import { GroupCdService } from 'app/entities/group-cd/group-cd.service';

describe('Component Tests', () => {
    describe('GroupCd Management Delete Component', () => {
        let comp: GroupCdDeleteDialogComponent;
        let fixture: ComponentFixture<GroupCdDeleteDialogComponent>;
        let service: GroupCdService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [GroupCdDeleteDialogComponent]
            })
                .overrideTemplate(GroupCdDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroupCdDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupCdService);
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
