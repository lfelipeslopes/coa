import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAutomaticOperation } from 'app/shared/model/automatic-operation.model';

type EntityResponseType = HttpResponse<IAutomaticOperation>;
type EntityArrayResponseType = HttpResponse<IAutomaticOperation[]>;

@Injectable({ providedIn: 'root' })
export class AutomaticOperationService {
    public resourceUrl = SERVER_API_URL + 'api/automatic-operations';

    constructor(protected http: HttpClient) {}

    create(automaticOperation: IAutomaticOperation): Observable<EntityResponseType> {
        return this.http.post<IAutomaticOperation>(this.resourceUrl, automaticOperation, { observe: 'response' });
    }

    update(automaticOperation: IAutomaticOperation): Observable<EntityResponseType> {
        return this.http.put<IAutomaticOperation>(this.resourceUrl, automaticOperation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAutomaticOperation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAutomaticOperation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
