import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInformativeOperation } from 'app/shared/model/informative-operation.model';
import { AccountService } from 'app/core';
import { InformativeOperationService } from './informative-operation.service';

@Component({
    selector: 'jhi-informative-operation',
    templateUrl: './informative-operation.component.html'
})
export class InformativeOperationComponent implements OnInit, OnDestroy {
    informativeOperations: IInformativeOperation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected informativeOperationService: InformativeOperationService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.informativeOperationService
            .query()
            .pipe(
                filter((res: HttpResponse<IInformativeOperation[]>) => res.ok),
                map((res: HttpResponse<IInformativeOperation[]>) => res.body)
            )
            .subscribe(
                (res: IInformativeOperation[]) => {
                    this.informativeOperations = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInInformativeOperations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInformativeOperation) {
        return item.id;
    }

    registerChangeInInformativeOperations() {
        this.eventSubscriber = this.eventManager.subscribe('informativeOperationListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
