/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Kyc5TestModule } from '../../../test.module';
import { InvestmentPotentialDeleteDialogComponent } from 'app/entities/investment-potential/investment-potential-delete-dialog.component';
import { InvestmentPotentialService } from 'app/entities/investment-potential/investment-potential.service';

describe('Component Tests', () => {
    describe('InvestmentPotential Management Delete Component', () => {
        let comp: InvestmentPotentialDeleteDialogComponent;
        let fixture: ComponentFixture<InvestmentPotentialDeleteDialogComponent>;
        let service: InvestmentPotentialService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [InvestmentPotentialDeleteDialogComponent]
            })
                .overrideTemplate(InvestmentPotentialDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvestmentPotentialDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvestmentPotentialService);
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
