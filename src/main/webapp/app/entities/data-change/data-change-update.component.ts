import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDataChange } from 'app/shared/model/data-change.model';
import { DataChangeService } from './data-change.service';

@Component({
    selector: 'jhi-data-change-update',
    templateUrl: './data-change-update.component.html'
})
export class DataChangeUpdateComponent implements OnInit {
    dataChange: IDataChange;
    isSaving: boolean;

    constructor(protected dataChangeService: DataChangeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dataChange }) => {
            this.dataChange = dataChange;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dataChange.id !== undefined) {
            this.subscribeToSaveResponse(this.dataChangeService.update(this.dataChange));
        } else {
            this.subscribeToSaveResponse(this.dataChangeService.create(this.dataChange));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataChange>>) {
        result.subscribe((res: HttpResponse<IDataChange>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
