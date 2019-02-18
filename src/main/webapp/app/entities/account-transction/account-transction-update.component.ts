import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAccountTransction } from 'app/shared/model/account-transction.model';
import { AccountTransctionService } from './account-transction.service';
import { IAccountOperation } from 'app/shared/model/account-operation.model';
import { AccountOperationService } from 'app/entities/account-operation';

@Component({
    selector: 'jhi-account-transction-update',
    templateUrl: './account-transction-update.component.html'
})
export class AccountTransctionUpdateComponent implements OnInit {
    accountTransction: IAccountTransction;
    isSaving: boolean;

    accountoperations: IAccountOperation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected accountTransctionService: AccountTransctionService,
        protected accountOperationService: AccountOperationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accountTransction }) => {
            this.accountTransction = accountTransction;
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
        if (this.accountTransction.id !== undefined) {
            this.subscribeToSaveResponse(this.accountTransctionService.update(this.accountTransction));
        } else {
            this.subscribeToSaveResponse(this.accountTransctionService.create(this.accountTransction));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountTransction>>) {
        result.subscribe((res: HttpResponse<IAccountTransction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
