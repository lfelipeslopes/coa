import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from './billing-location.service';
import { IPassage } from 'app/shared/model/passage.model';
import { PassageService } from 'app/entities/passage';

@Component({
    selector: 'jhi-billing-location-update',
    templateUrl: './billing-location-update.component.html'
})
export class BillingLocationUpdateComponent implements OnInit {
    billingLocation: IBillingLocation;
    isSaving: boolean;

    ids: IPassage[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected billingLocationService: BillingLocationService,
        protected passageService: PassageService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ billingLocation }) => {
            this.billingLocation = billingLocation;
        });
        this.passageService
            .query({ filter: 'billinglocation-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPassage[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPassage[]>) => response.body)
            )
            .subscribe(
                (res: IPassage[]) => {
                    if (!this.billingLocation.idId) {
                        this.ids = res;
                    } else {
                        this.passageService
                            .find(this.billingLocation.idId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPassage>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPassage>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPassage) => (this.ids = [subRes].concat(res)),
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
        if (this.billingLocation.id !== undefined) {
            this.subscribeToSaveResponse(this.billingLocationService.update(this.billingLocation));
        } else {
            this.subscribeToSaveResponse(this.billingLocationService.create(this.billingLocation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillingLocation>>) {
        result.subscribe((res: HttpResponse<IBillingLocation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPassageById(index: number, item: IPassage) {
        return item.id;
    }
}