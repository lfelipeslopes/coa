import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAutomaticOperation } from 'app/shared/model/automatic-operation.model';
import { AutomaticOperationService } from './automatic-operation.service';
import { IAccountOperation } from 'app/shared/model/account-operation.model';
import { AccountOperationService } from 'app/entities/account-operation';

@Component({
    selector: 'jhi-automatic-operation-update',
    templateUrl: './automatic-operation-update.component.html'
})
export class AutomaticOperationUpdateComponent implements OnInit {
    automaticOperation: IAutomaticOperation;
    isSaving: boolean;

    accountoperations: IAccountOperation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected automaticOperationService: AutomaticOperationService,
        protected accountOperationService: AccountOperationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ automaticOperation }) => {
            this.automaticOperation = automaticOperation;
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
        if (this.automaticOperation.id !== undefined) {
            this.subscribeToSaveResponse(this.automaticOperationService.update(this.automaticOperation));
        } else {
            this.subscribeToSaveResponse(this.automaticOperationService.create(this.automaticOperation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutomaticOperation>>) {
        result.subscribe((res: HttpResponse<IAutomaticOperation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
