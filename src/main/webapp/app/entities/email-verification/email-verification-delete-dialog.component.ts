import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmailVerification } from 'app/shared/model/email-verification.model';
import { EmailVerificationService } from './email-verification.service';

@Component({
    selector: 'jhi-email-verification-delete-dialog',
    templateUrl: './email-verification-delete-dialog.component.html'
})
export class EmailVerificationDeleteDialogComponent {
    emailVerification: IEmailVerification;

    constructor(
        private emailVerificationService: EmailVerificationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emailVerificationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'emailVerificationListModification',
                content: 'Deleted an emailVerification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-email-verification-delete-popup',
    template: ''
})
export class EmailVerificationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ emailVerification }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmailVerificationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.emailVerification = emailVerification;
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
