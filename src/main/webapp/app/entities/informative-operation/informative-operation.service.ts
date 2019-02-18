import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInformativeOperation } from 'app/shared/model/informative-operation.model';

type EntityResponseType = HttpResponse<IInformativeOperation>;
type EntityArrayResponseType = HttpResponse<IInformativeOperation[]>;

@Injectable({ providedIn: 'root' })
export class InformativeOperationService {
    public resourceUrl = SERVER_API_URL + 'api/informative-operations';

    constructor(protected http: HttpClient) {}

    create(informativeOperation: IInformativeOperation): Observable<EntityResponseType> {
        return this.http.post<IInformativeOperation>(this.resourceUrl, informativeOperation, { observe: 'response' });
    }

    update(informativeOperation: IInformativeOperation): Observable<EntityResponseType> {
        return this.http.put<IInformativeOperation>(this.resourceUrl, informativeOperation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IInformativeOperation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInformativeOperation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
