import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFinancialAccount } from 'app/shared/model/financial-account.model';

@Component({
    selector: 'jhi-financial-account-detail',
    templateUrl: './financial-account-detail.component.html'
})
export class FinancialAccountDetailComponent implements OnInit {
    financialAccount: IFinancialAccount;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ financialAccount }) => {
            this.financialAccount = financialAccount;
        });
    }

    previousState() {
        window.history.back();
    }
}
