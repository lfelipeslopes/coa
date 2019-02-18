import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBillingTariff } from 'app/shared/model/billing-tariff.model';

type EntityResponseType = HttpResponse<IBillingTariff>;
type EntityArrayResponseType = HttpResponse<IBillingTariff[]>;

@Injectable({ providedIn: 'root' })
export class BillingTariffService {
    public resourceUrl = SERVER_API_URL + 'api/billing-tariffs';

    constructor(protected http: HttpClient) {}

    create(billingTariff: IBillingTariff): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(billingTariff);
        return this.http
            .post<IBillingTariff>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(billingTariff: IBillingTariff): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(billingTariff);
        return this.http
            .put<IBillingTariff>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBillingTariff>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBillingTariff[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(billingTariff: IBillingTariff): IBillingTariff {
        const copy: IBillingTariff = Object.assign({}, billingTariff, {
            startedIn: billingTariff.startedIn != null && billingTariff.startedIn.isValid() ? billingTariff.startedIn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startedIn = res.body.startedIn != null ? moment(res.body.startedIn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((billingTariff: IBillingTariff) => {
                billingTariff.startedIn = billingTariff.startedIn != null ? moment(billingTariff.startedIn) : null;
            });
        }
        return res;
    }
}
