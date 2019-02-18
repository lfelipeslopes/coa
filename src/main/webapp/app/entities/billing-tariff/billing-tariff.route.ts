import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BillingTariff } from 'app/shared/model/billing-tariff.model';
import { BillingTariffService } from './billing-tariff.service';
import { BillingTariffComponent } from './billing-tariff.component';
import { BillingTariffDetailComponent } from './billing-tariff-detail.component';
import { BillingTariffUpdateComponent } from './billing-tariff-update.component';
import { BillingTariffDeletePopupComponent } from './billing-tariff-delete-dialog.component';
import { IBillingTariff } from 'app/shared/model/billing-tariff.model';

@Injectable({ providedIn: 'root' })
export class BillingTariffResolve implements Resolve<IBillingTariff> {
    constructor(private service: BillingTariffService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBillingTariff> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BillingTariff>) => response.ok),
                map((billingTariff: HttpResponse<BillingTariff>) => billingTariff.body)
            );
        }
        return of(new BillingTariff());
    }
}

export const billingTariffRoute: Routes = [
    {
        path: '',
        component: BillingTariffComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingTariffs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BillingTariffDetailComponent,
        resolve: {
            billingTariff: BillingTariffResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingTariffs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BillingTariffUpdateComponent,
        resolve: {
            billingTariff: BillingTariffResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingTariffs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BillingTariffUpdateComponent,
        resolve: {
            billingTariff: BillingTariffResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingTariffs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const billingTariffPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BillingTariffDeletePopupComponent,
        resolve: {
            billingTariff: BillingTariffResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingTariffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
