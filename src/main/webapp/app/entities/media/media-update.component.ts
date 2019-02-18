import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMedia } from 'app/shared/model/media.model';
import { MediaService } from './media.service';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from 'app/entities/vehicle';

@Component({
    selector: 'jhi-media-update',
    templateUrl: './media-update.component.html'
})
export class MediaUpdateComponent implements OnInit {
    media: IMedia;
    isSaving: boolean;

    vehicles: IVehicle[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected mediaService: MediaService,
        protected vehicleService: VehicleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ media }) => {
            this.media = media;
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
        if (this.media.id !== undefined) {
            this.subscribeToSaveResponse(this.mediaService.update(this.media));
        } else {
            this.subscribeToSaveResponse(this.mediaService.create(this.media));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedia>>) {
        result.subscribe((res: HttpResponse<IMedia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
