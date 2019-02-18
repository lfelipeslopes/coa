import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAccountTransction } from 'app/shared/model/account-transction.model';
import { AccountService } from 'app/core';
import { AccountTransctionService } from './account-transction.service';

@Component({
    selector: 'jhi-account-transction',
    templateUrl: './account-transction.component.html'
})
export class AccountTransctionComponent implements OnInit, OnDestroy {
    accountTransctions: IAccountTransction[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected accountTransctionService: AccountTransctionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.accountTransctionService
            .query()
            .pipe(
                filter((res: HttpResponse<IAccountTransction[]>) => res.ok),
                map((res: HttpResponse<IAccountTransction[]>) => res.body)
            )
            .subscribe(
                (res: IAccountTransction[]) => {
                    this.accountTransctions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAccountTransctions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAccountTransction) {
        return item.id;
    }

    registerChangeInAccountTransctions() {
        this.eventSubscriber = this.eventManager.subscribe('accountTransctionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
