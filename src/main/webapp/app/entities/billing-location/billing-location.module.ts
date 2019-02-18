import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    BillingLocationComponent,
    BillingLocationDetailComponent,
    BillingLocationUpdateComponent,
    BillingLocationDeletePopupComponent,
    BillingLocationDeleteDialogComponent,
    billingLocationRoute,
    billingLocationPopupRoute
} from './';

const ENTITY_STATES = [...billingLocationRoute, ...billingLocationPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BillingLocationComponent,
        BillingLocationDetailComponent,
        BillingLocationUpdateComponent,
        BillingLocationDeleteDialogComponent,
        BillingLocationDeletePopupComponent
    ],
    entryComponents: [
        BillingLocationComponent,
        BillingLocationUpdateComponent,
        BillingLocationDeleteDialogComponent,
        BillingLocationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaBillingLocationModule {}
