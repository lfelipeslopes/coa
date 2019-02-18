import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVehicleAccount } from 'app/shared/model/vehicle-account.model';

type EntityResponseType = HttpResponse<IVehicleAccount>;
type EntityArrayResponseType = HttpResponse<IVehicleAccount[]>;

@Injectable({ providedIn: 'root' })
export class VehicleAccountService {
    public resourceUrl = SERVER_API_URL + 'api/vehicle-accounts';

    constructor(protected http: HttpClient) {}

    create(vehicleAccount: IVehicleAccount): Observable<EntityResponseType> {
        return this.http.post<IVehicleAccount>(this.resourceUrl, vehicleAccount, { observe: 'response' });
    }

    update(vehicleAccount: IVehicleAccount): Observable<EntityResponseType> {
        return this.http.put<IVehicleAccount>(this.resourceUrl, vehicleAccount, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVehicleAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVehicleAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
