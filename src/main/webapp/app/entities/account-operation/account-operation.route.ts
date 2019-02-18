import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountOperation } from 'app/shared/model/account-operation.model';
import { AccountOperationService } from './account-operation.service';
import { AccountOperationComponent } from './account-operation.component';
import { AccountOperationDetailComponent } from './account-operation-detail.component';
import { AccountOperationUpdateComponent } from './account-operation-update.component';
import { AccountOperationDeletePopupComponent } from './account-operation-delete-dialog.component';
import { IAccountOperation } from 'app/shared/model/account-operation.model';

@Injectable({ providedIn: 'root' })
export class AccountOperationResolve implements Resolve<IAccountOperation> {
    constructor(private service: AccountOperationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountOperation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AccountOperation>) => response.ok),
                map((accountOperation: HttpResponse<AccountOperation>) => accountOperation.body)
            );
        }
        return of(new AccountOperation());
    }
}

export const accountOperationRoute: Routes = [
    {
        path: '',
        component: AccountOperationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountOperations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AccountOperationDetailComponent,
        resolve: {
            accountOperation: AccountOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountOperations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AccountOperationUpdateComponent,
        resolve: {
            accountOperation: AccountOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountOperations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AccountOperationUpdateComponent,
        resolve: {
            accountOperation: AccountOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountOperations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accountOperationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AccountOperationDeletePopupComponent,
        resolve: {
            accountOperation: AccountOperationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountOperations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
