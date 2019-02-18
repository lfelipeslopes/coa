import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataChange } from 'app/shared/model/data-change.model';

type EntityResponseType = HttpResponse<IDataChange>;
type EntityArrayResponseType = HttpResponse<IDataChange[]>;

@Injectable({ providedIn: 'root' })
export class DataChangeService {
    public resourceUrl = SERVER_API_URL + 'api/data-changes';

    constructor(protected http: HttpClient) {}

    create(dataChange: IDataChange): Observable<EntityResponseType> {
        return this.http.post<IDataChange>(this.resourceUrl, dataChange, { observe: 'response' });
    }

    update(dataChange: IDataChange): Observable<EntityResponseType> {
        return this.http.put<IDataChange>(this.resourceUrl, dataChange, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDataChange>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDataChange[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
