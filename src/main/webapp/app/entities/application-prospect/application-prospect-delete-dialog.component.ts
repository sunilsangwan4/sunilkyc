import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from './application-prospect.service';

@Component({
    selector: 'jhi-application-prospect-delete-dialog',
    templateUrl: './application-prospect-delete-dialog.component.html'
})
export class ApplicationProspectDeleteDialogComponent {
    applicationProspect: IApplicationProspect;

    constructor(
        private applicationProspectService: ApplicationProspectService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.applicationProspectService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'applicationProspectListModification',
                content: 'Deleted an applicationProspect'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-application-prospect-delete-popup',
    template: ''
})
export class ApplicationProspectDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ applicationProspect }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApplicationProspectDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.applicationProspect = applicationProspect;
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
