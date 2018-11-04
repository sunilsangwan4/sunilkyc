import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBankInformation } from 'app/shared/model/bank-information.model';
import { BankInformationService } from './bank-information.service';

@Component({
    selector: 'jhi-bank-information-delete-dialog',
    templateUrl: './bank-information-delete-dialog.component.html'
})
export class BankInformationDeleteDialogComponent {
    bankInformation: IBankInformation;

    constructor(
        private bankInformationService: BankInformationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bankInformationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bankInformationListModification',
                content: 'Deleted an bankInformation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bank-information-delete-popup',
    template: ''
})
export class BankInformationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bankInformation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BankInformationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bankInformation = bankInformation;
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
