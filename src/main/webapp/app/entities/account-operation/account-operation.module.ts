import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    AccountOperationComponent,
    AccountOperationDetailComponent,
    AccountOperationUpdateComponent,
    AccountOperationDeletePopupComponent,
    AccountOperationDeleteDialogComponent,
    accountOperationRoute,
    accountOperationPopupRoute
} from './';

const ENTITY_STATES = [...accountOperationRoute, ...accountOperationPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccountOperationComponent,
        AccountOperationDetailComponent,
        AccountOperationUpdateComponent,
        AccountOperationDeleteDialogComponent,
        AccountOperationDeletePopupComponent
    ],
    entryComponents: [
        AccountOperationComponent,
        AccountOperationUpdateComponent,
        AccountOperationDeleteDialogComponent,
        AccountOperationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaAccountOperationModule {}
