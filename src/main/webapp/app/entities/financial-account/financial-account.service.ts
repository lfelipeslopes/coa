import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFinancialAccount } from 'app/shared/model/financial-account.model';

type EntityResponseType = HttpResponse<IFinancialAccount>;
type EntityArrayResponseType = HttpResponse<IFinancialAccount[]>;

@Injectable({ providedIn: 'root' })
export class FinancialAccountService {
    public resourceUrl = SERVER_API_URL + 'api/financial-accounts';

    constructor(protected http: HttpClient) {}

    create(financialAccount: IFinancialAccount): Observable<EntityResponseType> {
        return this.http.post<IFinancialAccount>(this.resourceUrl, financialAccount, { observe: 'response' });
    }

    update(financialAccount: IFinancialAccount): Observable<EntityResponseType> {
        return this.http.put<IFinancialAccount>(this.resourceUrl, financialAccount, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFinancialAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFinancialAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
