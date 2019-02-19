import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IVehicleClass } from 'app/shared/model/vehicle-class.model';
import { VehicleClassService } from './vehicle-class.service';

@Component({
    selector: 'jhi-vehicle-class-update',
    templateUrl: './vehicle-class-update.component.html'
})
export class VehicleClassUpdateComponent implements OnInit {
    vehicleClass: IVehicleClass;
    isSaving: boolean;

    constructor(protected vehicleClassService: VehicleClassService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vehicleClass }) => {
            this.vehicleClass = vehicleClass;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vehicleClass.id !== undefined) {
            this.subscribeToSaveResponse(this.vehicleClassService.update(this.vehicleClass));
        } else {
            this.subscribeToSaveResponse(this.vehicleClassService.create(this.vehicleClass));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleClass>>) {
        result.subscribe((res: HttpResponse<IVehicleClass>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
