import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPassage } from 'app/shared/model/passage.model';
import { AccountService } from 'app/core';
import { PassageService } from './passage.service';

@Component({
    selector: 'jhi-passage',
    templateUrl: './passage.component.html'
})
export class PassageComponent implements OnInit, OnDestroy {
    passages: IPassage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected passageService: PassageService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.passageService
            .query()
            .pipe(
                filter((res: HttpResponse<IPassage[]>) => res.ok),
                map((res: HttpResponse<IPassage[]>) => res.body)
            )
            .subscribe(
                (res: IPassage[]) => {
                    this.passages = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPassages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPassage) {
        return item.id;
    }

    registerChangeInPassages() {
        this.eventSubscriber = this.eventManager.subscribe('passageListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
