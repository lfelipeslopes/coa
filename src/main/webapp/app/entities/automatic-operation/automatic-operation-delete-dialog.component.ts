import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAutomaticOperation } from 'app/shared/model/automatic-operation.model';
import { AutomaticOperationService } from './automatic-operation.service';

@Component({
    selector: 'jhi-automatic-operation-delete-dialog',
    templateUrl: './automatic-operation-delete-dialog.component.html'
})
export class AutomaticOperationDeleteDialogComponent {
    automaticOperation: IAutomaticOperation;

    constructor(
        protected automaticOperationService: AutomaticOperationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.automaticOperationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'automaticOperationListModification',
                content: 'Deleted an automaticOperation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-automatic-operation-delete-popup',
    template: ''
})
export class AutomaticOperationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ automaticOperation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AutomaticOperationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.automaticOperation = automaticOperation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/automatic-operation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/automatic-operation', { outlets: { popup: null } }]);
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
