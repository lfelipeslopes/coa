import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    AccountTransctionComponent,
    AccountTransctionDetailComponent,
    AccountTransctionUpdateComponent,
    AccountTransctionDeletePopupComponent,
    AccountTransctionDeleteDialogComponent,
    accountTransctionRoute,
    accountTransctionPopupRoute
} from './';

const ENTITY_STATES = [...accountTransctionRoute, ...accountTransctionPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccountTransctionComponent,
        AccountTransctionDetailComponent,
        AccountTransctionUpdateComponent,
        AccountTransctionDeleteDialogComponent,
        AccountTransctionDeletePopupComponent
    ],
    entryComponents: [
        AccountTransctionComponent,
        AccountTransctionUpdateComponent,
        AccountTransctionDeleteDialogComponent,
        AccountTransctionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaAccountTransctionModule {}
