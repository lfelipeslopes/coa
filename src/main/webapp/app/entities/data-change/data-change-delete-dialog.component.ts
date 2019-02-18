import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataChange } from 'app/shared/model/data-change.model';
import { DataChangeService } from './data-change.service';

@Component({
    selector: 'jhi-data-change-delete-dialog',
    templateUrl: './data-change-delete-dialog.component.html'
})
export class DataChangeDeleteDialogComponent {
    dataChange: IDataChange;

    constructor(
        protected dataChangeService: DataChangeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataChangeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dataChangeListModification',
                content: 'Deleted an dataChange'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-data-change-delete-popup',
    template: ''
})
export class DataChangeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataChange }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DataChangeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.dataChange = dataChange;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/data-change', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/data-change', { outlets: { popup: null } }]);
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
