import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountTransction } from 'app/shared/model/account-transction.model';
import { AccountTransctionService } from './account-transction.service';

@Component({
    selector: 'jhi-account-transction-delete-dialog',
    templateUrl: './account-transction-delete-dialog.component.html'
})
export class AccountTransctionDeleteDialogComponent {
    accountTransction: IAccountTransction;

    constructor(
        protected accountTransctionService: AccountTransctionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.accountTransctionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'accountTransctionListModification',
                content: 'Deleted an accountTransction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-account-transction-delete-popup',
    template: ''
})
export class AccountTransctionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountTransction }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AccountTransctionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.accountTransction = accountTransction;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/account-transction', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/account-transction', { outlets: { popup: null } }]);
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
