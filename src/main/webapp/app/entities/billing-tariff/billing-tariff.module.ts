import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    BillingTariffComponent,
    BillingTariffDetailComponent,
    BillingTariffUpdateComponent,
    BillingTariffDeletePopupComponent,
    BillingTariffDeleteDialogComponent,
    billingTariffRoute,
    billingTariffPopupRoute
} from './';

const ENTITY_STATES = [...billingTariffRoute, ...billingTariffPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BillingTariffComponent,
        BillingTariffDetailComponent,
        BillingTariffUpdateComponent,
        BillingTariffDeleteDialogComponent,
        BillingTariffDeletePopupComponent
    ],
    entryComponents: [
        BillingTariffComponent,
        BillingTariffUpdateComponent,
        BillingTariffDeleteDialogComponent,
        BillingTariffDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaBillingTariffModule {}
