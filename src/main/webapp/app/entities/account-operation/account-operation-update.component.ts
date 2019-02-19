import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IAccountOperation } from 'app/shared/model/account-operation.model';
import { AccountOperationService } from './account-operation.service';

@Component({
    selector: 'jhi-account-operation-update',
    templateUrl: './account-operation-update.component.html'
})
export class AccountOperationUpdateComponent implements OnInit {
    accountOperation: IAccountOperation;
    isSaving: boolean;
    occurrenceDate: string;

    constructor(protected accountOperationService: AccountOperationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accountOperation }) => {
            this.accountOperation = accountOperation;
            this.occurrenceDate =
                this.accountOperation.occurrenceDate != null ? this.accountOperation.occurrenceDate.format(DATE_TIME_FORMAT) : null;
        });
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
}
