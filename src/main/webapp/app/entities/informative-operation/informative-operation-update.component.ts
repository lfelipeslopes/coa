import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IInformativeOperation } from 'app/shared/model/informative-operation.model';
import { InformativeOperationService } from './informative-operation.service';

@Component({
    selector: 'jhi-informative-operation-update',
    templateUrl: './informative-operation-update.component.html'
})
export class InformativeOperationUpdateComponent implements OnInit {
    informativeOperation: IInformativeOperation;
    isSaving: boolean;

    constructor(protected informativeOperationService: InformativeOperationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ informativeOperation }) => {
            this.informativeOperation = informativeOperation;
        });
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
}
