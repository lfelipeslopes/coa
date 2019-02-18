import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVehicleClass } from 'app/shared/model/vehicle-class.model';
import { AccountService } from 'app/core';
import { VehicleClassService } from './vehicle-class.service';

@Component({
    selector: 'jhi-vehicle-class',
    templateUrl: './vehicle-class.component.html'
})
export class VehicleClassComponent implements OnInit, OnDestroy {
    vehicleClasses: IVehicleClass[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected vehicleClassService: VehicleClassService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.vehicleClassService
            .query()
            .pipe(
                filter((res: HttpResponse<IVehicleClass[]>) => res.ok),
                map((res: HttpResponse<IVehicleClass[]>) => res.body)
            )
            .subscribe(
                (res: IVehicleClass[]) => {
                    this.vehicleClasses = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVehicleClasses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVehicleClass) {
        return item.id;
    }

    registerChangeInVehicleClasses() {
        this.eventSubscriber = this.eventManager.subscribe('vehicleClassListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
