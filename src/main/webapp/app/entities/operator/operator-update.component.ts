import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOperator } from 'app/shared/model/operator.model';
import { OperatorService } from './operator.service';
import { IAccountOperation } from 'app/shared/model/account-operation.model';
import { AccountOperationService } from 'app/entities/account-operation';

@Component({
    selector: 'jhi-operator-update',
    templateUrl: './operator-update.component.html'
})
export class OperatorUpdateComponent implements OnInit {
    operator: IOperator;
    isSaving: boolean;

    accountoperations: IAccountOperation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected operatorService: OperatorService,
        protected accountOperationService: AccountOperationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operator }) => {
            this.operator = operator;
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
        if (this.operator.id !== undefined) {
            this.subscribeToSaveResponse(this.operatorService.update(this.operator));
        } else {
            this.subscribeToSaveResponse(this.operatorService.create(this.operator));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperator>>) {
        result.subscribe((res: HttpResponse<IOperator>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
