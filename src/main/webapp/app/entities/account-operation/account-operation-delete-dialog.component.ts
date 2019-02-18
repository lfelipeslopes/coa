import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountOperation } from 'app/shared/model/account-operation.model';
import { AccountOperationService } from './account-operation.service';

@Component({
    selector: 'jhi-account-operation-delete-dialog',
    templateUrl: './account-operation-delete-dialog.component.html'
})
export class AccountOperationDeleteDialogComponent {
    accountOperation: IAccountOperation;

    constructor(
        protected accountOperationService: AccountOperationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.accountOperationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'accountOperationListModification',
                content: 'Deleted an accountOperation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-account-operation-delete-popup',
    template: ''
})
export class AccountOperationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountOperation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AccountOperationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.accountOperation = accountOperation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/account-operation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/account-operation', { outlets: { popup: null } }]);
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
