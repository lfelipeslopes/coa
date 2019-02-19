import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IBillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from './billing-location.service';

@Component({
    selector: 'jhi-billing-location-update',
    templateUrl: './billing-location-update.component.html'
})
export class BillingLocationUpdateComponent implements OnInit {
    billingLocation: IBillingLocation;
    isSaving: boolean;

    constructor(protected billingLocationService: BillingLocationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ billingLocation }) => {
            this.billingLocation = billingLocation;
        });
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
}
