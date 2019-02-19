import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IBillingTariff } from 'app/shared/model/billing-tariff.model';
import { BillingTariffService } from './billing-tariff.service';

@Component({
    selector: 'jhi-billing-tariff-update',
    templateUrl: './billing-tariff-update.component.html'
})
export class BillingTariffUpdateComponent implements OnInit {
    billingTariff: IBillingTariff;
    isSaving: boolean;
    startedIn: string;

    constructor(protected billingTariffService: BillingTariffService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ billingTariff }) => {
            this.billingTariff = billingTariff;
            this.startedIn = this.billingTariff.startedIn != null ? this.billingTariff.startedIn.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.billingTariff.startedIn = this.startedIn != null ? moment(this.startedIn, DATE_TIME_FORMAT) : null;
        if (this.billingTariff.id !== undefined) {
            this.subscribeToSaveResponse(this.billingTariffService.update(this.billingTariff));
        } else {
            this.subscribeToSaveResponse(this.billingTariffService.create(this.billingTariff));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillingTariff>>) {
        result.subscribe((res: HttpResponse<IBillingTariff>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
