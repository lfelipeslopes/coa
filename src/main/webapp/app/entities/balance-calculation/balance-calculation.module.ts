import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    BalanceCalculationComponent,
    BalanceCalculationDetailComponent,
    BalanceCalculationUpdateComponent,
    BalanceCalculationDeletePopupComponent,
    BalanceCalculationDeleteDialogComponent,
    balanceCalculationRoute,
    balanceCalculationPopupRoute
} from './';

const ENTITY_STATES = [...balanceCalculationRoute, ...balanceCalculationPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BalanceCalculationComponent,
        BalanceCalculationDetailComponent,
        BalanceCalculationUpdateComponent,
        BalanceCalculationDeleteDialogComponent,
        BalanceCalculationDeletePopupComponent
    ],
    entryComponents: [
        BalanceCalculationComponent,
        BalanceCalculationUpdateComponent,
        BalanceCalculationDeleteDialogComponent,
        BalanceCalculationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaBalanceCalculationModule {}
