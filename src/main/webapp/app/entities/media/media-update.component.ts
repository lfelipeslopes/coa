import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMedia } from 'app/shared/model/media.model';
import { MediaService } from './media.service';

@Component({
    selector: 'jhi-media-update',
    templateUrl: './media-update.component.html'
})
export class MediaUpdateComponent implements OnInit {
    media: IMedia;
    isSaving: boolean;

    constructor(protected mediaService: MediaService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ media }) => {
            this.media = media;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.media.id !== undefined) {
            this.subscribeToSaveResponse(this.mediaService.update(this.media));
        } else {
            this.subscribeToSaveResponse(this.mediaService.create(this.media));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedia>>) {
        result.subscribe((res: HttpResponse<IMedia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
