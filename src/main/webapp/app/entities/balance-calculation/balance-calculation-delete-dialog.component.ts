import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBalanceCalculation } from 'app/shared/model/balance-calculation.model';
import { BalanceCalculationService } from './balance-calculation.service';

@Component({
    selector: 'jhi-balance-calculation-delete-dialog',
    templateUrl: './balance-calculation-delete-dialog.component.html'
})
export class BalanceCalculationDeleteDialogComponent {
    balanceCalculation: IBalanceCalculation;

    constructor(
        protected balanceCalculationService: BalanceCalculationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.balanceCalculationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'balanceCalculationListModification',
                content: 'Deleted an balanceCalculation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-balance-calculation-delete-popup',
    template: ''
})
export class BalanceCalculationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ balanceCalculation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BalanceCalculationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.balanceCalculation = balanceCalculation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/balance-calculation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/balance-calculation', { outlets: { popup: null } }]);
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
