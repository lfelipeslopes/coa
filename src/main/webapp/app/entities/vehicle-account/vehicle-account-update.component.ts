import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVehicleAccount } from 'app/shared/model/vehicle-account.model';
import { VehicleAccountService } from './vehicle-account.service';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from 'app/entities/vehicle';

@Component({
    selector: 'jhi-vehicle-account-update',
    templateUrl: './vehicle-account-update.component.html'
})
export class VehicleAccountUpdateComponent implements OnInit {
    vehicleAccount: IVehicleAccount;
    isSaving: boolean;

    idvehicles: IVehicle[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected vehicleAccountService: VehicleAccountService,
        protected vehicleService: VehicleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vehicleAccount }) => {
            this.vehicleAccount = vehicleAccount;
        });
        this.vehicleService
            .query({ filter: 'vehicleaccount-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IVehicle[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVehicle[]>) => response.body)
            )
            .subscribe(
                (res: IVehicle[]) => {
                    if (!this.vehicleAccount.idVehicleId) {
                        this.idvehicles = res;
                    } else {
                        this.vehicleService
                            .find(this.vehicleAccount.idVehicleId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IVehicle>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IVehicle>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IVehicle) => (this.idvehicles = [subRes].concat(res)),
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
        if (this.vehicleAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.vehicleAccountService.update(this.vehicleAccount));
        } else {
            this.subscribeToSaveResponse(this.vehicleAccountService.create(this.vehicleAccount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleAccount>>) {
        result.subscribe((res: HttpResponse<IVehicleAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
