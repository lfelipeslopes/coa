import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataChange } from 'app/shared/model/data-change.model';
import { DataChangeService } from './data-change.service';
import { DataChangeComponent } from './data-change.component';
import { DataChangeDetailComponent } from './data-change-detail.component';
import { DataChangeUpdateComponent } from './data-change-update.component';
import { DataChangeDeletePopupComponent } from './data-change-delete-dialog.component';
import { IDataChange } from 'app/shared/model/data-change.model';

@Injectable({ providedIn: 'root' })
export class DataChangeResolve implements Resolve<IDataChange> {
    constructor(private service: DataChangeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataChange> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DataChange>) => response.ok),
                map((dataChange: HttpResponse<DataChange>) => dataChange.body)
            );
        }
        return of(new DataChange());
    }
}

export const dataChangeRoute: Routes = [
    {
        path: '',
        component: DataChangeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataChanges'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DataChangeDetailComponent,
        resolve: {
            dataChange: DataChangeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataChanges'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DataChangeUpdateComponent,
        resolve: {
            dataChange: DataChangeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataChanges'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DataChangeUpdateComponent,
        resolve: {
            dataChange: DataChangeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataChanges'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataChangePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DataChangeDeletePopupComponent,
        resolve: {
            dataChange: DataChangeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataChanges'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
