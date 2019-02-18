import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBillingLocation } from 'app/shared/model/billing-location.model';
import { AccountService } from 'app/core';
import { BillingLocationService } from './billing-location.service';

@Component({
    selector: 'jhi-billing-location',
    templateUrl: './billing-location.component.html'
})
export class BillingLocationComponent implements OnInit, OnDestroy {
    billingLocations: IBillingLocation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected billingLocationService: BillingLocationService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.billingLocationService
            .query()
            .pipe(
                filter((res: HttpResponse<IBillingLocation[]>) => res.ok),
                map((res: HttpResponse<IBillingLocation[]>) => res.body)
            )
            .subscribe(
                (res: IBillingLocation[]) => {
                    this.billingLocations = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBillingLocations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBillingLocation) {
        return item.id;
    }

    registerChangeInBillingLocations() {
        this.eventSubscriber = this.eventManager.subscribe('billingLocationListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
