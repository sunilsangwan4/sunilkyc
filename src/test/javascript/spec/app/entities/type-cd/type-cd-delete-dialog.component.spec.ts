/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Kyc5TestModule } from '../../../test.module';
import { TypeCdDeleteDialogComponent } from 'app/entities/type-cd/type-cd-delete-dialog.component';
import { TypeCdService } from 'app/entities/type-cd/type-cd.service';

describe('Component Tests', () => {
    describe('TypeCd Management Delete Component', () => {
        let comp: TypeCdDeleteDialogComponent;
        let fixture: ComponentFixture<TypeCdDeleteDialogComponent>;
        let service: TypeCdService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TypeCdDeleteDialogComponent]
            })
                .overrideTemplate(TypeCdDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeCdDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCdService);
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
