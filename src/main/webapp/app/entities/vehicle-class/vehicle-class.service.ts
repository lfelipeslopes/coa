import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVehicleClass } from 'app/shared/model/vehicle-class.model';

type EntityResponseType = HttpResponse<IVehicleClass>;
type EntityArrayResponseType = HttpResponse<IVehicleClass[]>;

@Injectable({ providedIn: 'root' })
export class VehicleClassService {
    public resourceUrl = SERVER_API_URL + 'api/vehicle-classes';

    constructor(protected http: HttpClient) {}

    create(vehicleClass: IVehicleClass): Observable<EntityResponseType> {
        return this.http.post<IVehicleClass>(this.resourceUrl, vehicleClass, { observe: 'response' });
    }

    update(vehicleClass: IVehicleClass): Observable<EntityResponseType> {
        return this.http.put<IVehicleClass>(this.resourceUrl, vehicleClass, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVehicleClass>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVehicleClass[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
