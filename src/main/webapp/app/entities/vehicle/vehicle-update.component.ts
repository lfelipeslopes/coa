import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from './vehicle.service';
import { IVehicleClass } from 'app/shared/model/vehicle-class.model';
import { VehicleClassService } from 'app/entities/vehicle-class';

@Component({
    selector: 'jhi-vehicle-update',
    templateUrl: './vehicle-update.component.html'
})
export class VehicleUpdateComponent implements OnInit {
    vehicle: IVehicle;
    isSaving: boolean;

    idvehicleclasses: IVehicleClass[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected vehicleService: VehicleService,
        protected vehicleClassService: VehicleClassService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vehicle }) => {
            this.vehicle = vehicle;
        });
        this.vehicleClassService
            .query({ filter: 'vehicle-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IVehicleClass[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVehicleClass[]>) => response.body)
            )
            .subscribe(
                (res: IVehicleClass[]) => {
                    if (!this.vehicle.idVehicleClassId) {
                        this.idvehicleclasses = res;
                    } else {
                        this.vehicleClassService
                            .find(this.vehicle.idVehicleClassId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IVehicleClass>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IVehicleClass>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IVehicleClass) => (this.idvehicleclasses = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vehicle.id !== undefined) {
            this.subscribeToSaveResponse(this.vehicleService.update(this.vehicle));
        } else {
            this.subscribeToSaveResponse(this.vehicleService.create(this.vehicle));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>) {
        result.subscribe((res: HttpResponse<IVehicle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVehicleClassById(index: number, item: IVehicleClass) {
        return item.id;
    }
}
