import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataChange } from 'app/shared/model/data-change.model';

@Component({
    selector: 'jhi-data-change-detail',
    templateUrl: './data-change-detail.component.html'
})
export class DataChangeDetailComponent implements OnInit {
    dataChange: IDataChange;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataChange }) => {
            this.dataChange = dataChange;
        });
    }

    previousState() {
        window.history.back();
    }
}
