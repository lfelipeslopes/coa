import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillingTariff } from 'app/shared/model/billing-tariff.model';

@Component({
    selector: 'jhi-billing-tariff-detail',
    templateUrl: './billing-tariff-detail.component.html'
})
export class BillingTariffDetailComponent implements OnInit {
    billingTariff: IBillingTariff;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ billingTariff }) => {
            this.billingTariff = billingTariff;
        });
    }

    previousState() {
        window.history.back();
    }
}
