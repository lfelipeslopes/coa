import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountTransction } from 'app/shared/model/account-transction.model';

type EntityResponseType = HttpResponse<IAccountTransction>;
type EntityArrayResponseType = HttpResponse<IAccountTransction[]>;

@Injectable({ providedIn: 'root' })
export class AccountTransctionService {
    public resourceUrl = SERVER_API_URL + 'api/account-transctions';

    constructor(protected http: HttpClient) {}

    create(accountTransction: IAccountTransction): Observable<EntityResponseType> {
        return this.http.post<IAccountTransction>(this.resourceUrl, accountTransction, { observe: 'response' });
    }

    update(accountTransction: IAccountTransction): Observable<EntityResponseType> {
        return this.http.put<IAccountTransction>(this.resourceUrl, accountTransction, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAccountTransction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAccountTransction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
