import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVehicleAccount } from 'app/shared/model/vehicle-account.model';
import { AccountService } from 'app/core';
import { VehicleAccountService } from './vehicle-account.service';

@Component({
    selector: 'jhi-vehicle-account',
    templateUrl: './vehicle-account.component.html'
})
export class VehicleAccountComponent implements OnInit, OnDestroy {
    vehicleAccounts: IVehicleAccount[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected vehicleAccountService: VehicleAccountService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.vehicleAccountService
            .query()
            .pipe(
                filter((res: HttpResponse<IVehicleAccount[]>) => res.ok),
                map((res: HttpResponse<IVehicleAccount[]>) => res.body)
            )
            .subscribe(
                (res: IVehicleAccount[]) => {
                    this.vehicleAccounts = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVehicleAccounts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVehicleAccount) {
        return item.id;
    }

    registerChangeInVehicleAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('vehicleAccountListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
