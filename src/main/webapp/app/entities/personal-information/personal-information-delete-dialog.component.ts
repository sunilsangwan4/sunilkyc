import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonalInformation } from 'app/shared/model/personal-information.model';
import { PersonalInformationService } from './personal-information.service';

@Component({
    selector: 'jhi-personal-information-delete-dialog',
    templateUrl: './personal-information-delete-dialog.component.html'
})
export class PersonalInformationDeleteDialogComponent {
    personalInformation: IPersonalInformation;

    constructor(
        private personalInformationService: PersonalInformationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personalInformationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personalInformationListModification',
                content: 'Deleted an personalInformation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personal-information-delete-popup',
    template: ''
})
export class PersonalInformationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personalInformation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PersonalInformationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.personalInformation = personalInformation;
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
