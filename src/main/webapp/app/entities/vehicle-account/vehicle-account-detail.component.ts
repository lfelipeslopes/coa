import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleAccount } from 'app/shared/model/vehicle-account.model';

@Component({
    selector: 'jhi-vehicle-account-detail',
    templateUrl: './vehicle-account-detail.component.html'
})
export class VehicleAccountDetailComponent implements OnInit {
    vehicleAccount: IVehicleAccount;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vehicleAccount }) => {
            this.vehicleAccount = vehicleAccount;
        });
    }

    previousState() {
        window.history.back();
    }
}
