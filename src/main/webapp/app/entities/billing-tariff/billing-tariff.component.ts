import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBillingTariff } from 'app/shared/model/billing-tariff.model';
import { AccountService } from 'app/core';
import { BillingTariffService } from './billing-tariff.service';

@Component({
    selector: 'jhi-billing-tariff',
    templateUrl: './billing-tariff.component.html'
})
export class BillingTariffComponent implements OnInit, OnDestroy {
    billingTariffs: IBillingTariff[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected billingTariffService: BillingTariffService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.billingTariffService
            .query()
            .pipe(
                filter((res: HttpResponse<IBillingTariff[]>) => res.ok),
                map((res: HttpResponse<IBillingTariff[]>) => res.body)
            )
            .subscribe(
                (res: IBillingTariff[]) => {
                    this.billingTariffs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBillingTariffs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBillingTariff) {
        return item.id;
    }

    registerChangeInBillingTariffs() {
        this.eventSubscriber = this.eventManager.subscribe('billingTariffListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
