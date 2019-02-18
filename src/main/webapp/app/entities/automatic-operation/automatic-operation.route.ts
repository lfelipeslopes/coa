import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AutomaticOperation } from 'app/shared/model/automatic-operation.model';
import { AutomaticOperationService } from './automatic-operation.service';
import { AutomaticOperationComponent } from './automatic-operation.component';
import { AutomaticOperationDetailComponent } from './automatic-operation-detail.component';
import { AutomaticOperationUpdateComponent } from './automatic-operation-update.component';
import { AutomaticOperationDeletePopupComponent } from './automatic-operation-delete-dialog.component';
import { IAutomaticOperation } from 'app/shared/model/automatic-operation.model';

@Injectable({ providedIn: 'root' })
export class AutomaticOperationResolve implements Resolve<IAutomaticOperation> {
    constructor(private service: AutomaticOperationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAutomaticOperation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AutomaticOperation>) => response.ok),
                map((automaticOperation: HttpResponse<AutomaticOperation>) => automaticOperation.body)
            );
        }
        return of(new AutomaticOperation());
    }
}

export const automaticOperationRoute: Routes = [
    {
        path: '',
        component: AutomaticOperationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AutomaticOperations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AutomaticOperationDetailComponent,
        resolve: {
            automaticOperation: AutomaticOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AutomaticOperations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AutomaticOperationUpdateComponent,
        resolve: {
            automaticOperation: AutomaticOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AutomaticOperations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AutomaticOperationUpdateComponent,
        resolve: {
            automaticOperation: AutomaticOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AutomaticOperations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const automaticOperationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AutomaticOperationDeletePopupComponent,
        resolve: {
            automaticOperation: AutomaticOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AutomaticOperations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
