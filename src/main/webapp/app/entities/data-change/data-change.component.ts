import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDataChange } from 'app/shared/model/data-change.model';
import { AccountService } from 'app/core';
import { DataChangeService } from './data-change.service';

@Component({
    selector: 'jhi-data-change',
    templateUrl: './data-change.component.html'
})
export class DataChangeComponent implements OnInit, OnDestroy {
    dataChanges: IDataChange[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected dataChangeService: DataChangeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.dataChangeService
            .query()
            .pipe(
                filter((res: HttpResponse<IDataChange[]>) => res.ok),
                map((res: HttpResponse<IDataChange[]>) => res.body)
            )
            .subscribe(
                (res: IDataChange[]) => {
                    this.dataChanges = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDataChanges();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDataChange) {
        return item.id;
    }

    registerChangeInDataChanges() {
        this.eventSubscriber = this.eventManager.subscribe('dataChangeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
