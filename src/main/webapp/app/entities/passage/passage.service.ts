import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPassage } from 'app/shared/model/passage.model';

type EntityResponseType = HttpResponse<IPassage>;
type EntityArrayResponseType = HttpResponse<IPassage[]>;

@Injectable({ providedIn: 'root' })
export class PassageService {
    public resourceUrl = SERVER_API_URL + 'api/passages';

    constructor(protected http: HttpClient) {}

    create(passage: IPassage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(passage);
        return this.http
            .post<IPassage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(passage: IPassage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(passage);
        return this.http
            .put<IPassage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPassage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPassage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(passage: IPassage): IPassage {
        const copy: IPassage = Object.assign({}, passage, {
            occurrenceDate: passage.occurrenceDate != null && passage.occurrenceDate.isValid() ? passage.occurrenceDate.toJSON() : null,
            processedAt: passage.processedAt != null && passage.processedAt.isValid() ? passage.processedAt.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.occurrenceDate = res.body.occurrenceDate != null ? moment(res.body.occurrenceDate) : null;
            res.body.processedAt = res.body.processedAt != null ? moment(res.body.processedAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((passage: IPassage) => {
                passage.occurrenceDate = passage.occurrenceDate != null ? moment(passage.occurrenceDate) : null;
                passage.processedAt = passage.processedAt != null ? moment(passage.processedAt) : null;
            });
        }
        return res;
    }
}
