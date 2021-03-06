import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillingLocation } from 'app/shared/model/billing-location.model';

@Component({
    selector: 'jhi-billing-location-detail',
    templateUrl: './billing-location-detail.component.html'
})
export class BillingLocationDetailComponent implements OnInit {
    billingLocation: IBillingLocation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ billingLocation }) => {
            this.billingLocation = billingLocation;
        });
    }

    previousState() {
        window.history.back();
    }
}
