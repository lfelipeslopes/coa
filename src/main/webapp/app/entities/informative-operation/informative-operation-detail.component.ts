import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInformativeOperation } from 'app/shared/model/informative-operation.model';

@Component({
    selector: 'jhi-informative-operation-detail',
    templateUrl: './informative-operation-detail.component.html'
})
export class InformativeOperationDetailComponent implements OnInit {
    informativeOperation: IInformativeOperation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ informativeOperation }) => {
            this.informativeOperation = informativeOperation;
        });
    }

    previousState() {
        window.history.back();
    }
}
