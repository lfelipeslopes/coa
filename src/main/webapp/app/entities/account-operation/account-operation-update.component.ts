import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAccountOperation } from 'app/shared/model/account-operation.model';
import { AccountOperationService } from './account-operation.service';
import { IFinancialAccount } from 'app/shared/model/financial-account.model';
import { FinancialAccountService } from 'app/entities/financial-account';

@Component({
    selector: 'jhi-account-operation-update',
    templateUrl: './account-operation-update.component.html'
})
export class AccountOperationUpdateComponent implements OnInit {
    accountOperation: IAccountOperation;
    isSaving: boolean;

    financialaccounts: IFinancialAccount[];
    occurrenceDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected accountOperationService: AccountOperationService,
        protected financialAccountService: FinancialAccountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accountOperation }) => {
            this.accountOperation = accountOperation;
            this.occurrenceDate =
                this.accountOperation.occurrenceDate != null ? this.accountOperation.occurrenceDate.format(DATE_TIME_FORMAT) : null;
        });
        this.financialAccountService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFinancialAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFinancialAccount[]>) => response.body)
            )
            .subscribe((res: IFinancialAccount[]) => (this.financialaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.accountOperation.occurrenceDate = this.occurrenceDate != null ? moment(this.occurrenceDate, DATE_TIME_FORMAT) : null;
        if (this.accountOperation.id !== undefined) {
            this.subscribeToSaveResponse(this.accountOperationService.update(this.accountOperation));
        } else {
            this.subscribeToSaveResponse(this.accountOperationService.create(this.accountOperation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountOperation>>) {
        result.subscribe((res: HttpResponse<IAccountOperation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFinancialAccountById(index: number, item: IFinancialAccount) {
        return item.id;
    }
}
