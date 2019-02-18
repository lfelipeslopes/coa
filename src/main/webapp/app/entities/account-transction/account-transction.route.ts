import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountTransction } from 'app/shared/model/account-transction.model';
import { AccountTransctionService } from './account-transction.service';
import { AccountTransctionComponent } from './account-transction.component';
import { AccountTransctionDetailComponent } from './account-transction-detail.component';
import { AccountTransctionUpdateComponent } from './account-transction-update.component';
import { AccountTransctionDeletePopupComponent } from './account-transction-delete-dialog.component';
import { IAccountTransction } from 'app/shared/model/account-transction.model';

@Injectable({ providedIn: 'root' })
export class AccountTransctionResolve implements Resolve<IAccountTransction> {
    constructor(private service: AccountTransctionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountTransction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AccountTransction>) => response.ok),
                map((accountTransction: HttpResponse<AccountTransction>) => accountTransction.body)
            );
        }
        return of(new AccountTransction());
    }
}

export const accountTransctionRoute: Routes = [
    {
        path: '',
        component: AccountTransctionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountTransctions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AccountTransctionDetailComponent,
        resolve: {
            accountTransction: AccountTransctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountTransctions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AccountTransctionUpdateComponent,
        resolve: {
            accountTransction: AccountTransctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountTransctions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AccountTransctionUpdateComponent,
        resolve: {
            accountTransction: AccountTransctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountTransctions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accountTransctionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AccountTransctionDeletePopupComponent,
        resolve: {
            accountTransction: AccountTransctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountTransctions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
