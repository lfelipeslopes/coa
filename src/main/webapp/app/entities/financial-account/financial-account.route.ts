import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FinancialAccount } from 'app/shared/model/financial-account.model';
import { FinancialAccountService } from './financial-account.service';
import { FinancialAccountComponent } from './financial-account.component';
import { FinancialAccountDetailComponent } from './financial-account-detail.component';
import { FinancialAccountUpdateComponent } from './financial-account-update.component';
import { FinancialAccountDeletePopupComponent } from './financial-account-delete-dialog.component';
import { IFinancialAccount } from 'app/shared/model/financial-account.model';

@Injectable({ providedIn: 'root' })
export class FinancialAccountResolve implements Resolve<IFinancialAccount> {
    constructor(private service: FinancialAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFinancialAccount> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FinancialAccount>) => response.ok),
                map((financialAccount: HttpResponse<FinancialAccount>) => financialAccount.body)
            );
        }
        return of(new FinancialAccount());
    }
}

export const financialAccountRoute: Routes = [
    {
        path: '',
        component: FinancialAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FinancialAccountDetailComponent,
        resolve: {
            financialAccount: FinancialAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FinancialAccountUpdateComponent,
        resolve: {
            financialAccount: FinancialAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FinancialAccountUpdateComponent,
        resolve: {
            financialAccount: FinancialAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const financialAccountPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FinancialAccountDeletePopupComponent,
        resolve: {
            financialAccount: FinancialAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
