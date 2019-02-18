import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInformativeOperation } from 'app/shared/model/informative-operation.model';
import { InformativeOperationService } from './informative-operation.service';
import { IAccountOperation } from 'app/shared/model/account-operation.model';
import { AccountOperationService } from 'app/entities/account-operation';

@Component({
    selector: 'jhi-informative-operation-update',
    templateUrl: './informative-operation-update.component.html'
})
export class InformativeOperationUpdateComponent implements OnInit {
    informativeOperation: IInformativeOperation;
    isSaving: boolean;

    accountoperations: IAccountOperation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected informativeOperationService: InformativeOperationService,
        protected accountOperationService: AccountOperationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ informativeOperation }) => {
            this.informativeOperation = informativeOperation;
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
        if (this.informativeOperation.id !== undefined) {
            this.subscribeToSaveResponse(this.informativeOperationService.update(this.informativeOperation));
        } else {
            this.subscribeToSaveResponse(this.informativeOperationService.create(this.informativeOperation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInformativeOperation>>) {
        result.subscribe(
            (res: HttpResponse<IInformativeOperation>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
