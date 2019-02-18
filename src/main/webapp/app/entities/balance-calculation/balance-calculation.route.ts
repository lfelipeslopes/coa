import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BalanceCalculation } from 'app/shared/model/balance-calculation.model';
import { BalanceCalculationService } from './balance-calculation.service';
import { BalanceCalculationComponent } from './balance-calculation.component';
import { BalanceCalculationDetailComponent } from './balance-calculation-detail.component';
import { BalanceCalculationUpdateComponent } from './balance-calculation-update.component';
import { BalanceCalculationDeletePopupComponent } from './balance-calculation-delete-dialog.component';
import { IBalanceCalculation } from 'app/shared/model/balance-calculation.model';

@Injectable({ providedIn: 'root' })
export class BalanceCalculationResolve implements Resolve<IBalanceCalculation> {
    constructor(private service: BalanceCalculationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBalanceCalculation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BalanceCalculation>) => response.ok),
                map((balanceCalculation: HttpResponse<BalanceCalculation>) => balanceCalculation.body)
            );
        }
        return of(new BalanceCalculation());
    }
}

export const balanceCalculationRoute: Routes = [
    {
        path: '',
        component: BalanceCalculationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BalanceCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BalanceCalculationDetailComponent,
        resolve: {
            balanceCalculation: BalanceCalculationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BalanceCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BalanceCalculationUpdateComponent,
        resolve: {
            balanceCalculation: BalanceCalculationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BalanceCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BalanceCalculationUpdateComponent,
        resolve: {
            balanceCalculation: BalanceCalculationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BalanceCalculations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const balanceCalculationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BalanceCalculationDeletePopupComponent,
        resolve: {
            balanceCalculation: BalanceCalculationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BalanceCalculations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
