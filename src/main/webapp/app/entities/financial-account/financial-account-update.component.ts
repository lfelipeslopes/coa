import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IFinancialAccount } from 'app/shared/model/financial-account.model';
import { FinancialAccountService } from './financial-account.service';

@Component({
    selector: 'jhi-financial-account-update',
    templateUrl: './financial-account-update.component.html'
})
export class FinancialAccountUpdateComponent implements OnInit {
    financialAccount: IFinancialAccount;
    isSaving: boolean;

    constructor(protected financialAccountService: FinancialAccountService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ financialAccount }) => {
            this.financialAccount = financialAccount;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.financialAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.financialAccountService.update(this.financialAccount));
        } else {
            this.subscribeToSaveResponse(this.financialAccountService.create(this.financialAccount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinancialAccount>>) {
        result.subscribe((res: HttpResponse<IFinancialAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
