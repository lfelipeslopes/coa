import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from './user-account.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
    selector: 'jhi-user-account-update',
    templateUrl: './user-account-update.component.html'
})
export class UserAccountUpdateComponent implements OnInit {
    userAccount: IUserAccount;
    isSaving: boolean;

    people: IPerson[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected userAccountService: UserAccountService,
        protected personService: PersonService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userAccount }) => {
            this.userAccount = userAccount;
        });
        this.personService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPerson[]>) => response.body)
            )
            .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.userAccountService.update(this.userAccount));
        } else {
            this.subscribeToSaveResponse(this.userAccountService.create(this.userAccount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserAccount>>) {
        result.subscribe((res: HttpResponse<IUserAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPersonById(index: number, item: IPerson) {
        return item.id;
    }
}
