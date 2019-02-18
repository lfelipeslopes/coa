import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VehicleClass } from 'app/shared/model/vehicle-class.model';
import { VehicleClassService } from './vehicle-class.service';
import { VehicleClassComponent } from './vehicle-class.component';
import { VehicleClassDetailComponent } from './vehicle-class-detail.component';
import { VehicleClassUpdateComponent } from './vehicle-class-update.component';
import { VehicleClassDeletePopupComponent } from './vehicle-class-delete-dialog.component';
import { IVehicleClass } from 'app/shared/model/vehicle-class.model';

@Injectable({ providedIn: 'root' })
export class VehicleClassResolve implements Resolve<IVehicleClass> {
    constructor(private service: VehicleClassService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVehicleClass> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VehicleClass>) => response.ok),
                map((vehicleClass: HttpResponse<VehicleClass>) => vehicleClass.body)
            );
        }
        return of(new VehicleClass());
    }
}

export const vehicleClassRoute: Routes = [
    {
        path: '',
        component: VehicleClassComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleClasses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VehicleClassDetailComponent,
        resolve: {
            vehicleClass: VehicleClassResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleClasses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VehicleClassUpdateComponent,
        resolve: {
            vehicleClass: VehicleClassResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleClasses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VehicleClassUpdateComponent,
        resolve: {
            vehicleClass: VehicleClassResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleClasses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vehicleClassPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VehicleClassDeletePopupComponent,
        resolve: {
            vehicleClass: VehicleClassResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleClasses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
