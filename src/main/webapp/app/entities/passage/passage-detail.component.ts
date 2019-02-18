import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPassage } from 'app/shared/model/passage.model';

@Component({
    selector: 'jhi-passage-detail',
    templateUrl: './passage-detail.component.html'
})
export class PassageDetailComponent implements OnInit {
    passage: IPassage;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ passage }) => {
            this.passage = passage;
        });
    }

    previousState() {
        window.history.back();
    }
}
