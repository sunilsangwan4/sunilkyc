import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITokenVerification } from 'app/shared/model/token-verification.model';
import { TokenVerificationService } from './token-verification.service';

@Component({
    selector: 'jhi-token-verification-delete-dialog',
    templateUrl: './token-verification-delete-dialog.component.html'
})
export class TokenVerificationDeleteDialogComponent {
    tokenVerification: ITokenVerification;

    constructor(
        private tokenVerificationService: TokenVerificationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tokenVerificationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tokenVerificationListModification',
                content: 'Deleted an tokenVerification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-token-verification-delete-popup',
    template: ''
})
export class TokenVerificationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tokenVerification }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TokenVerificationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tokenVerification = tokenVerification;
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
