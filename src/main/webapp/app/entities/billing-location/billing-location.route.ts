import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from './billing-location.service';
import { BillingLocationComponent } from './billing-location.component';
import { BillingLocationDetailComponent } from './billing-location-detail.component';
import { BillingLocationUpdateComponent } from './billing-location-update.component';
import { BillingLocationDeletePopupComponent } from './billing-location-delete-dialog.component';
import { IBillingLocation } from 'app/shared/model/billing-location.model';

@Injectable({ providedIn: 'root' })
export class BillingLocationResolve implements Resolve<IBillingLocation> {
    constructor(private service: BillingLocationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBillingLocation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BillingLocation>) => response.ok),
                map((billingLocation: HttpResponse<BillingLocation>) => billingLocation.body)
            );
        }
        return of(new BillingLocation());
    }
}

export const billingLocationRoute: Routes = [
    {
        path: '',
        component: BillingLocationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BillingLocationDetailComponent,
        resolve: {
            billingLocation: BillingLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BillingLocationUpdateComponent,
        resolve: {
            billingLocation: BillingLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BillingLocationUpdateComponent,
        resolve: {
            billingLocation: BillingLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const billingLocationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BillingLocationDeletePopupComponent,
        resolve: {
            billingLocation: BillingLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BillingLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
