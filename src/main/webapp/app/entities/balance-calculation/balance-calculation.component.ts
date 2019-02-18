import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBalanceCalculation } from 'app/shared/model/balance-calculation.model';
import { AccountService } from 'app/core';
import { BalanceCalculationService } from './balance-calculation.service';

@Component({
    selector: 'jhi-balance-calculation',
    templateUrl: './balance-calculation.component.html'
})
export class BalanceCalculationComponent implements OnInit, OnDestroy {
    balanceCalculations: IBalanceCalculation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected balanceCalculationService: BalanceCalculationService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.balanceCalculationService
            .query()
            .pipe(
                filter((res: HttpResponse<IBalanceCalculation[]>) => res.ok),
                map((res: HttpResponse<IBalanceCalculation[]>) => res.body)
            )
            .subscribe(
                (res: IBalanceCalculation[]) => {
                    this.balanceCalculations = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBalanceCalculations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBalanceCalculation) {
        return item.id;
    }

    registerChangeInBalanceCalculations() {
        this.eventSubscriber = this.eventManager.subscribe('balanceCalculationListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
