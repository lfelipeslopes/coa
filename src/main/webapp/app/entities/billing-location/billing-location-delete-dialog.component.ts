import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from './billing-location.service';

@Component({
    selector: 'jhi-billing-location-delete-dialog',
    templateUrl: './billing-location-delete-dialog.component.html'
})
export class BillingLocationDeleteDialogComponent {
    billingLocation: IBillingLocation;

    constructor(
        protected billingLocationService: BillingLocationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.billingLocationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'billingLocationListModification',
                content: 'Deleted an billingLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-billing-location-delete-popup',
    template: ''
})
export class BillingLocationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ billingLocation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BillingLocationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.billingLocation = billingLocation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/billing-location', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/billing-location', { outlets: { popup: null } }]);
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
