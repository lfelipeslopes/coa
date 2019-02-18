import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountOperation } from 'app/shared/model/account-operation.model';

@Component({
    selector: 'jhi-account-operation-detail',
    templateUrl: './account-operation-detail.component.html'
})
export class AccountOperationDetailComponent implements OnInit {
    accountOperation: IAccountOperation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountOperation }) => {
            this.accountOperation = accountOperation;
        });
    }

    previousState() {
        window.history.back();
    }
}
