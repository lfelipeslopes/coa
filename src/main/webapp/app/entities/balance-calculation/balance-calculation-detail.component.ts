import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBalanceCalculation } from 'app/shared/model/balance-calculation.model';

@Component({
    selector: 'jhi-balance-calculation-detail',
    templateUrl: './balance-calculation-detail.component.html'
})
export class BalanceCalculationDetailComponent implements OnInit {
    balanceCalculation: IBalanceCalculation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ balanceCalculation }) => {
            this.balanceCalculation = balanceCalculation;
        });
    }

    previousState() {
        window.history.back();
    }
}
