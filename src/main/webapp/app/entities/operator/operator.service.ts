import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOperator } from 'app/shared/model/operator.model';

type EntityResponseType = HttpResponse<IOperator>;
type EntityArrayResponseType = HttpResponse<IOperator[]>;

@Injectable({ providedIn: 'root' })
export class OperatorService {
    public resourceUrl = SERVER_API_URL + 'api/operators';

    constructor(protected http: HttpClient) {}

    create(operator: IOperator): Observable<EntityResponseType> {
        return this.http.post<IOperator>(this.resourceUrl, operator, { observe: 'response' });
    }

    update(operator: IOperator): Observable<EntityResponseType> {
        return this.http.put<IOperator>(this.resourceUrl, operator, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOperator>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOperator[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
