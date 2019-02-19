import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPassage } from 'app/shared/model/passage.model';
import { PassageService } from './passage.service';
import { IVehicleClass } from 'app/shared/model/vehicle-class.model';
import { VehicleClassService } from 'app/entities/vehicle-class';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from 'app/entities/vehicle';

@Component({
    selector: 'jhi-passage-update',
    templateUrl: './passage-update.component.html'
})
export class PassageUpdateComponent implements OnInit {
    passage: IPassage;
    isSaving: boolean;

    vehicleclasses: IVehicleClass[];

    vehicles: IVehicle[];
    occurrenceDate: string;
    processedAt: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected passageService: PassageService,
        protected vehicleClassService: VehicleClassService,
        protected vehicleService: VehicleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ passage }) => {
            this.passage = passage;
            this.occurrenceDate = this.passage.occurrenceDate != null ? this.passage.occurrenceDate.format(DATE_TIME_FORMAT) : null;
            this.processedAt = this.passage.processedAt != null ? this.passage.processedAt.format(DATE_TIME_FORMAT) : null;
        });
        this.vehicleClassService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVehicleClass[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVehicleClass[]>) => response.body)
            )
            .subscribe((res: IVehicleClass[]) => (this.vehicleclasses = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.passage.occurrenceDate = this.occurrenceDate != null ? moment(this.occurrenceDate, DATE_TIME_FORMAT) : null;
        this.passage.processedAt = this.processedAt != null ? moment(this.processedAt, DATE_TIME_FORMAT) : null;
        if (this.passage.id !== undefined) {
            this.subscribeToSaveResponse(this.passageService.update(this.passage));
        } else {
            this.subscribeToSaveResponse(this.passageService.create(this.passage));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPassage>>) {
        result.subscribe((res: HttpResponse<IPassage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVehicleById(index: number, item: IVehicle) {
        return item.id;
    }
}
