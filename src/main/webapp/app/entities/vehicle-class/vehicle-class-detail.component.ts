import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleClass } from 'app/shared/model/vehicle-class.model';

@Component({
    selector: 'jhi-vehicle-class-detail',
    templateUrl: './vehicle-class-detail.component.html'
})
export class VehicleClassDetailComponent implements OnInit {
    vehicleClass: IVehicleClass;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vehicleClass }) => {
            this.vehicleClass = vehicleClass;
        });
    }

    previousState() {
        window.history.back();
    }
}
