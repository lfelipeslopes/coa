import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountOperation } from 'app/shared/model/account-operation.model';

type EntityResponseType = HttpResponse<IAccountOperation>;
type EntityArrayResponseType = HttpResponse<IAccountOperation[]>;

@Injectable({ providedIn: 'root' })
export class AccountOperationService {
    public resourceUrl = SERVER_API_URL + 'api/account-operations';

    constructor(protected http: HttpClient) {}

    create(accountOperation: IAccountOperation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(accountOperation);
        return this.http
            .post<IAccountOperation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(accountOperation: IAccountOperation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(accountOperation);
        return this.http
            .put<IAccountOperation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAccountOperation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAccountOperation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(accountOperation: IAccountOperation): IAccountOperation {
        const copy: IAccountOperation = Object.assign({}, accountOperation, {
            occurrenceDate:
                accountOperation.occurrenceDate != null && accountOperation.occurrenceDate.isValid()
                    ? accountOperation.occurrenceDate.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.occurrenceDate = res.body.occurrenceDate != null ? moment(res.body.occurrenceDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((accountOperation: IAccountOperation) => {
                accountOperation.occurrenceDate = accountOperation.occurrenceDate != null ? moment(accountOperation.occurrenceDate) : null;
            });
        }
        return res;
    }
}
