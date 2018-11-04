import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupCd } from 'app/shared/model/group-cd.model';
import { GroupCdService } from './group-cd.service';

@Component({
    selector: 'jhi-group-cd-delete-dialog',
    templateUrl: './group-cd-delete-dialog.component.html'
})
export class GroupCdDeleteDialogComponent {
    groupCd: IGroupCd;

    constructor(private groupCdService: GroupCdService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.groupCdService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'groupCdListModification',
                content: 'Deleted an groupCd'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-group-cd-delete-popup',
    template: ''
})
export class GroupCdDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groupCd }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GroupCdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.groupCd = groupCd;
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
