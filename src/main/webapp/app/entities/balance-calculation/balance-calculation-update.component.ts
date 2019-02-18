import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBalanceCalculation } from 'app/shared/model/balance-calculation.model';
import { BalanceCalculationService } from './balance-calculation.service';
import { IAccountOperation } from 'app/shared/model/account-operation.model';
import { AccountOperationService } from 'app/entities/account-operation';

@Component({
    selector: 'jhi-balance-calculation-update',
    templateUrl: './balance-calculation-update.component.html'
})
export class BalanceCalculationUpdateComponent implements OnInit {
    balanceCalculation: IBalanceCalculation;
    isSaving: boolean;

    accountoperations: IAccountOperation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected balanceCalculationService: BalanceCalculationService,
        protected accountOperationService: AccountOperationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ balanceCalculation }) => {
            this.balanceCalculation = balanceCalculation;
        });
        this.accountOperationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAccountOperation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAccountOperation[]>) => response.body)
            )
            .subscribe((res: IAccountOperation[]) => (this.accountoperations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.balanceCalculation.id !== undefined) {
            this.subscribeToSaveResponse(this.balanceCalculationService.update(this.balanceCalculation));
        } else {
            this.subscribeToSaveResponse(this.balanceCalculationService.create(this.balanceCalculation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBalanceCalculation>>) {
        result.subscribe((res: HttpResponse<IBalanceCalculation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAccountOperationById(index: number, item: IAccountOperation) {
        return item.id;
    }
}
