import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingTariff } from 'app/shared/model/billing-tariff.model';
import { BillingTariffService } from './billing-tariff.service';

@Component({
    selector: 'jhi-billing-tariff-delete-dialog',
    templateUrl: './billing-tariff-delete-dialog.component.html'
})
export class BillingTariffDeleteDialogComponent {
    billingTariff: IBillingTariff;

    constructor(
        protected billingTariffService: BillingTariffService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.billingTariffService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'billingTariffListModification',
                content: 'Deleted an billingTariff'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-billing-tariff-delete-popup',
    template: ''
})
export class BillingTariffDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ billingTariff }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BillingTariffDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.billingTariff = billingTariff;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/billing-tariff', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/billing-tariff', { outlets: { popup: null } }]);
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
