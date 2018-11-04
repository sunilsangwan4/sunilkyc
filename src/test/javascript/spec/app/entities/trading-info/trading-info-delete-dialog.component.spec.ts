/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Kyc5TestModule } from '../../../test.module';
import { TradingInfoDeleteDialogComponent } from 'app/entities/trading-info/trading-info-delete-dialog.component';
import { TradingInfoService } from 'app/entities/trading-info/trading-info.service';

describe('Component Tests', () => {
    describe('TradingInfo Management Delete Component', () => {
        let comp: TradingInfoDeleteDialogComponent;
        let fixture: ComponentFixture<TradingInfoDeleteDialogComponent>;
        let service: TradingInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TradingInfoDeleteDialogComponent]
            })
                .overrideTemplate(TradingInfoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TradingInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TradingInfoService);
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
