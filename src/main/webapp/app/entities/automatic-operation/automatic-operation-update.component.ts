import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAutomaticOperation } from 'app/shared/model/automatic-operation.model';
import { AutomaticOperationService } from './automatic-operation.service';

@Component({
    selector: 'jhi-automatic-operation-update',
    templateUrl: './automatic-operation-update.component.html'
})
export class AutomaticOperationUpdateComponent implements OnInit {
    automaticOperation: IAutomaticOperation;
    isSaving: boolean;

    constructor(protected automaticOperationService: AutomaticOperationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ automaticOperation }) => {
            this.automaticOperation = automaticOperation;
        });
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
}
