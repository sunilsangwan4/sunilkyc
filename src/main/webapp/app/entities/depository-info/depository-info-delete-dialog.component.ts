import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepositoryInfo } from 'app/shared/model/depository-info.model';
import { DepositoryInfoService } from './depository-info.service';

@Component({
    selector: 'jhi-depository-info-delete-dialog',
    templateUrl: './depository-info-delete-dialog.component.html'
})
export class DepositoryInfoDeleteDialogComponent {
    depositoryInfo: IDepositoryInfo;

    constructor(
        private depositoryInfoService: DepositoryInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.depositoryInfoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'depositoryInfoListModification',
                content: 'Deleted an depositoryInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-depository-info-delete-popup',
    template: ''
})
export class DepositoryInfoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depositoryInfo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DepositoryInfoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.depositoryInfo = depositoryInfo;
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
