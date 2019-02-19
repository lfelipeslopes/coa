import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from './billing-location.service';
import { IBillingTariff } from 'app/shared/model/billing-tariff.model';
import { BillingTariffService } from 'app/entities/billing-tariff';

@Component({
    selector: 'jhi-billing-location-update',
    templateUrl: './billing-location-update.component.html'
})
export class BillingLocationUpdateComponent implements OnInit {
    billingLocation: IBillingLocation;
    isSaving: boolean;

    idbillinglocations: IBillingTariff[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected billingLocationService: BillingLocationService,
        protected billingTariffService: BillingTariffService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ billingLocation }) => {
            this.billingLocation = billingLocation;
        });
        this.billingTariffService
            .query({ filter: 'billinglocation-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IBillingTariff[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBillingTariff[]>) => response.body)
            )
            .subscribe(
                (res: IBillingTariff[]) => {
                    if (!this.billingLocation.idBillingLocationId) {
                        this.idbillinglocations = res;
                    } else {
                        this.billingTariffService
                            .find(this.billingLocation.idBillingLocationId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IBillingTariff>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IBillingTariff>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IBillingTariff) => (this.idbillinglocations = [subRes].concat(res)),
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

    trackBillingTariffById(index: number, item: IBillingTariff) {
        return item.id;
    }
}
