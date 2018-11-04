import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvestmentPotential } from 'app/shared/model/investment-potential.model';
import { InvestmentPotentialService } from './investment-potential.service';

@Component({
    selector: 'jhi-investment-potential-delete-dialog',
    templateUrl: './investment-potential-delete-dialog.component.html'
})
export class InvestmentPotentialDeleteDialogComponent {
    investmentPotential: IInvestmentPotential;

    constructor(
        private investmentPotentialService: InvestmentPotentialService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.investmentPotentialService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'investmentPotentialListModification',
                content: 'Deleted an investmentPotential'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-investment-potential-delete-popup',
    template: ''
})
export class InvestmentPotentialDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ investmentPotential }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InvestmentPotentialDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.investmentPotential = investmentPotential;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
