import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVehicleClass } from 'app/shared/model/vehicle-class.model';
import { VehicleClassService } from './vehicle-class.service';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from 'app/entities/vehicle';

@Component({
    selector: 'jhi-vehicle-class-update',
    templateUrl: './vehicle-class-update.component.html'
})
export class VehicleClassUpdateComponent implements OnInit {
    vehicleClass: IVehicleClass;
    isSaving: boolean;

    vehicles: IVehicle[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected vehicleClassService: VehicleClassService,
        protected vehicleService: VehicleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vehicleClass }) => {
            this.vehicleClass = vehicleClass;
        });
        this.vehicleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVehicle[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVehicle[]>) => response.body)
            )
            .subscribe((res: IVehicle[]) => (this.vehicles = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackVehicleById(index: number, item: IVehicle) {
        return item.id;
    }
}
