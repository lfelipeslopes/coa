import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IBalanceCalculation } from 'app/shared/model/balance-calculation.model';
import { BalanceCalculationService } from './balance-calculation.service';

@Component({
    selector: 'jhi-balance-calculation-update',
    templateUrl: './balance-calculation-update.component.html'
})
export class BalanceCalculationUpdateComponent implements OnInit {
    balanceCalculation: IBalanceCalculation;
    isSaving: boolean;

    constructor(protected balanceCalculationService: BalanceCalculationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ balanceCalculation }) => {
            this.balanceCalculation = balanceCalculation;
        });
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
}
