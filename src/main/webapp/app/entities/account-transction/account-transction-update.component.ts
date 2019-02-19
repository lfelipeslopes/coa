import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAccountTransction } from 'app/shared/model/account-transction.model';
import { AccountTransctionService } from './account-transction.service';

@Component({
    selector: 'jhi-account-transction-update',
    templateUrl: './account-transction-update.component.html'
})
export class AccountTransctionUpdateComponent implements OnInit {
    accountTransction: IAccountTransction;
    isSaving: boolean;

    constructor(protected accountTransctionService: AccountTransctionService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accountTransction }) => {
            this.accountTransction = accountTransction;
        });
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
}
