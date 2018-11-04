import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITradingInfo } from 'app/shared/model/trading-info.model';
import { TradingInfoService } from './trading-info.service';

@Component({
    selector: 'jhi-trading-info-delete-dialog',
    templateUrl: './trading-info-delete-dialog.component.html'
})
export class TradingInfoDeleteDialogComponent {
    tradingInfo: ITradingInfo;

    constructor(
        private tradingInfoService: TradingInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tradingInfoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tradingInfoListModification',
                content: 'Deleted an tradingInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-trading-info-delete-popup',
    template: ''
})
export class TradingInfoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tradingInfo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TradingInfoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tradingInfo = tradingInfo;
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
