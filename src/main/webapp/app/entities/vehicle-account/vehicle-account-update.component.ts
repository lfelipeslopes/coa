import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVehicleAccount } from 'app/shared/model/vehicle-account.model';
import { VehicleAccountService } from './vehicle-account.service';
import { IVehicleClass } from 'app/shared/model/vehicle-class.model';
import { VehicleClassService } from 'app/entities/vehicle-class';

@Component({
    selector: 'jhi-vehicle-account-update',
    templateUrl: './vehicle-account-update.component.html'
})
export class VehicleAccountUpdateComponent implements OnInit {
    vehicleAccount: IVehicleAccount;
    isSaving: boolean;

    idvehicleclasses: IVehicleClass[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected vehicleAccountService: VehicleAccountService,
        protected vehicleClassService: VehicleClassService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vehicleAccount }) => {
            this.vehicleAccount = vehicleAccount;
        });
        this.vehicleClassService
            .query({ filter: 'vehicleaccount-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IVehicleClass[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVehicleClass[]>) => response.body)
            )
            .subscribe(
                (res: IVehicleClass[]) => {
                    if (!this.vehicleAccount.idVehicleClassId) {
                        this.idvehicleclasses = res;
                    } else {
                        this.vehicleClassService
                            .find(this.vehicleAccount.idVehicleClassId)
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

    trackVehicleClassById(index: number, item: IVehicleClass) {
        return item.id;
    }
}
