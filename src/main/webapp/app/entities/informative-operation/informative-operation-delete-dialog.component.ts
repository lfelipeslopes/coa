import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInformativeOperation } from 'app/shared/model/informative-operation.model';
import { InformativeOperationService } from './informative-operation.service';

@Component({
    selector: 'jhi-informative-operation-delete-dialog',
    templateUrl: './informative-operation-delete-dialog.component.html'
})
export class InformativeOperationDeleteDialogComponent {
    informativeOperation: IInformativeOperation;

    constructor(
        protected informativeOperationService: InformativeOperationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.informativeOperationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'informativeOperationListModification',
                content: 'Deleted an informativeOperation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-informative-operation-delete-popup',
    template: ''
})
export class InformativeOperationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ informativeOperation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InformativeOperationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.informativeOperation = informativeOperation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/informative-operation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/informative-operation', { outlets: { popup: null } }]);
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
