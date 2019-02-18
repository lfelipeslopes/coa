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
import { IBillingTariff } from 'app/shared/model/billing-tariff.model';
import { BillingTariffService } from 'app/entities/billing-tariff';

@Component({
    selector: 'jhi-vehicle-class-update',
    templateUrl: './vehicle-class-update.component.html'
})
export class VehicleClassUpdateComponent implements OnInit {
    vehicleClass: IVehicleClass;
    isSaving: boolean;

    vehicles: IVehicle[];

    ids: IBillingTariff[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected vehicleClassService: VehicleClassService,
        protected vehicleService: VehicleService,
        protected billingTariffService: BillingTariffService,
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
        this.billingTariffService
            .query({ filter: 'vehicleclass-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IBillingTariff[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBillingTariff[]>) => response.body)
            )
            .subscribe(
                (res: IBillingTariff[]) => {
                    if (!this.vehicleClass.idId) {
                        this.ids = res;
                    } else {
                        this.billingTariffService
                            .find(this.vehicleClass.idId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IBillingTariff>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IBillingTariff>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IBillingTariff) => (this.ids = [subRes].concat(res)),
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

    trackBillingTariffById(index: number, item: IBillingTariff) {
        return item.id;
    }
}
