import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAutomaticOperation } from 'app/shared/model/automatic-operation.model';

@Component({
    selector: 'jhi-automatic-operation-detail',
    templateUrl: './automatic-operation-detail.component.html'
})
export class AutomaticOperationDetailComponent implements OnInit {
    automaticOperation: IAutomaticOperation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ automaticOperation }) => {
            this.automaticOperation = automaticOperation;
        });
    }

    previousState() {
        window.history.back();
    }
}
