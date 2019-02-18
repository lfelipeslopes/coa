import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountTransction } from 'app/shared/model/account-transction.model';

@Component({
    selector: 'jhi-account-transction-detail',
    templateUrl: './account-transction-detail.component.html'
})
export class AccountTransctionDetailComponent implements OnInit {
    accountTransction: IAccountTransction;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountTransction }) => {
            this.accountTransction = accountTransction;
        });
    }

    previousState() {
        window.history.back();
    }
}
