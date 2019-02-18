import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    FinancialAccountComponent,
    FinancialAccountDetailComponent,
    FinancialAccountUpdateComponent,
    FinancialAccountDeletePopupComponent,
    FinancialAccountDeleteDialogComponent,
    financialAccountRoute,
    financialAccountPopupRoute
} from './';

const ENTITY_STATES = [...financialAccountRoute, ...financialAccountPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FinancialAccountComponent,
        FinancialAccountDetailComponent,
        FinancialAccountUpdateComponent,
        FinancialAccountDeleteDialogComponent,
        FinancialAccountDeletePopupComponent
    ],
    entryComponents: [
        FinancialAccountComponent,
        FinancialAccountUpdateComponent,
        FinancialAccountDeleteDialogComponent,
        FinancialAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaFinancialAccountModule {}
