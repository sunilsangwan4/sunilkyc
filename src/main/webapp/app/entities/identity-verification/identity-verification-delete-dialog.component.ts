import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdentityVerification } from 'app/shared/model/identity-verification.model';
import { IdentityVerificationService } from './identity-verification.service';

@Component({
    selector: 'jhi-identity-verification-delete-dialog',
    templateUrl: './identity-verification-delete-dialog.component.html'
})
export class IdentityVerificationDeleteDialogComponent {
    identityVerification: IIdentityVerification;

    constructor(
        private identityVerificationService: IdentityVerificationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.identityVerificationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'identityVerificationListModification',
                content: 'Deleted an identityVerification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-identity-verification-delete-popup',
    template: ''
})
export class IdentityVerificationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ identityVerification }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IdentityVerificationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.identityVerification = identityVerification;
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
