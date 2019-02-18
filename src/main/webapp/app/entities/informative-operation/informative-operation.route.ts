import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InformativeOperation } from 'app/shared/model/informative-operation.model';
import { InformativeOperationService } from './informative-operation.service';
import { InformativeOperationComponent } from './informative-operation.component';
import { InformativeOperationDetailComponent } from './informative-operation-detail.component';
import { InformativeOperationUpdateComponent } from './informative-operation-update.component';
import { InformativeOperationDeletePopupComponent } from './informative-operation-delete-dialog.component';
import { IInformativeOperation } from 'app/shared/model/informative-operation.model';

@Injectable({ providedIn: 'root' })
export class InformativeOperationResolve implements Resolve<IInformativeOperation> {
    constructor(private service: InformativeOperationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInformativeOperation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<InformativeOperation>) => response.ok),
                map((informativeOperation: HttpResponse<InformativeOperation>) => informativeOperation.body)
            );
        }
        return of(new InformativeOperation());
    }
}

export const informativeOperationRoute: Routes = [
    {
        path: '',
        component: InformativeOperationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InformativeOperations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InformativeOperationDetailComponent,
        resolve: {
            informativeOperation: InformativeOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InformativeOperations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InformativeOperationUpdateComponent,
        resolve: {
            informativeOperation: InformativeOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InformativeOperations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InformativeOperationUpdateComponent,
        resolve: {
            informativeOperation: InformativeOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InformativeOperations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const informativeOperationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: InformativeOperationDeletePopupComponent,
        resolve: {
            informativeOperation: InformativeOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InformativeOperations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
