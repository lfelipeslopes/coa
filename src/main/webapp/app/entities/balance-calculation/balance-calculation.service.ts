import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBalanceCalculation } from 'app/shared/model/balance-calculation.model';

type EntityResponseType = HttpResponse<IBalanceCalculation>;
type EntityArrayResponseType = HttpResponse<IBalanceCalculation[]>;

@Injectable({ providedIn: 'root' })
export class BalanceCalculationService {
    public resourceUrl = SERVER_API_URL + 'api/balance-calculations';

    constructor(protected http: HttpClient) {}

    create(balanceCalculation: IBalanceCalculation): Observable<EntityResponseType> {
        return this.http.post<IBalanceCalculation>(this.resourceUrl, balanceCalculation, { observe: 'response' });
    }

    update(balanceCalculation: IBalanceCalculation): Observable<EntityResponseType> {
        return this.http.put<IBalanceCalculation>(this.resourceUrl, balanceCalculation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBalanceCalculation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBalanceCalculation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
