import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    InformativeOperationComponent,
    InformativeOperationDetailComponent,
    InformativeOperationUpdateComponent,
    InformativeOperationDeletePopupComponent,
    InformativeOperationDeleteDialogComponent,
    informativeOperationRoute,
    informativeOperationPopupRoute
} from './';

const ENTITY_STATES = [...informativeOperationRoute, ...informativeOperationPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InformativeOperationComponent,
        InformativeOperationDetailComponent,
        InformativeOperationUpdateComponent,
        InformativeOperationDeleteDialogComponent,
        InformativeOperationDeletePopupComponent
    ],
    entryComponents: [
        InformativeOperationComponent,
        InformativeOperationUpdateComponent,
        InformativeOperationDeleteDialogComponent,
        InformativeOperationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaInformativeOperationModule {}
