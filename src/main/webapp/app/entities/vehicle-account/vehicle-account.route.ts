import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VehicleAccount } from 'app/shared/model/vehicle-account.model';
import { VehicleAccountService } from './vehicle-account.service';
import { VehicleAccountComponent } from './vehicle-account.component';
import { VehicleAccountDetailComponent } from './vehicle-account-detail.component';
import { VehicleAccountUpdateComponent } from './vehicle-account-update.component';
import { VehicleAccountDeletePopupComponent } from './vehicle-account-delete-dialog.component';
import { IVehicleAccount } from 'app/shared/model/vehicle-account.model';

@Injectable({ providedIn: 'root' })
export class VehicleAccountResolve implements Resolve<IVehicleAccount> {
    constructor(private service: VehicleAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVehicleAccount> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VehicleAccount>) => response.ok),
                map((vehicleAccount: HttpResponse<VehicleAccount>) => vehicleAccount.body)
            );
        }
        return of(new VehicleAccount());
    }
}

export const vehicleAccountRoute: Routes = [
    {
        path: '',
        component: VehicleAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VehicleAccountDetailComponent,
        resolve: {
            vehicleAccount: VehicleAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VehicleAccountUpdateComponent,
        resolve: {
            vehicleAccount: VehicleAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VehicleAccountUpdateComponent,
        resolve: {
            vehicleAccount: VehicleAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vehicleAccountPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VehicleAccountDeletePopupComponent,
        resolve: {
            vehicleAccount: VehicleAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
