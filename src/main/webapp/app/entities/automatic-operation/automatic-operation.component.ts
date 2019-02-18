import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAutomaticOperation } from 'app/shared/model/automatic-operation.model';
import { AccountService } from 'app/core';
import { AutomaticOperationService } from './automatic-operation.service';

@Component({
    selector: 'jhi-automatic-operation',
    templateUrl: './automatic-operation.component.html'
})
export class AutomaticOperationComponent implements OnInit, OnDestroy {
    automaticOperations: IAutomaticOperation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected automaticOperationService: AutomaticOperationService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.automaticOperationService
            .query()
            .pipe(
                filter((res: HttpResponse<IAutomaticOperation[]>) => res.ok),
                map((res: HttpResponse<IAutomaticOperation[]>) => res.body)
            )
            .subscribe(
                (res: IAutomaticOperation[]) => {
                    this.automaticOperations = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAutomaticOperations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAutomaticOperation) {
        return item.id;
    }

    registerChangeInAutomaticOperations() {
        this.eventSubscriber = this.eventManager.subscribe('automaticOperationListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
