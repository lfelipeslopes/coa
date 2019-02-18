import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFinancialAccount } from 'app/shared/model/financial-account.model';
import { FinancialAccountService } from './financial-account.service';

@Component({
    selector: 'jhi-financial-account-delete-dialog',
    templateUrl: './financial-account-delete-dialog.component.html'
})
export class FinancialAccountDeleteDialogComponent {
    financialAccount: IFinancialAccount;

    constructor(
        protected financialAccountService: FinancialAccountService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.financialAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'financialAccountListModification',
                content: 'Deleted an financialAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-financial-account-delete-popup',
    template: ''
})
export class FinancialAccountDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ financialAccount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FinancialAccountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.financialAccount = financialAccount;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/financial-account', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/financial-account', { outlets: { popup: null } }]);
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
