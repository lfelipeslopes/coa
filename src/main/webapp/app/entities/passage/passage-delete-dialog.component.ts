import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPassage } from 'app/shared/model/passage.model';
import { PassageService } from './passage.service';

@Component({
    selector: 'jhi-passage-delete-dialog',
    templateUrl: './passage-delete-dialog.component.html'
})
export class PassageDeleteDialogComponent {
    passage: IPassage;

    constructor(protected passageService: PassageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.passageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'passageListModification',
                content: 'Deleted an passage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-passage-delete-popup',
    template: ''
})
export class PassageDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ passage }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PassageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.passage = passage;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/passage', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/passage', { outlets: { popup: null } }]);
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
