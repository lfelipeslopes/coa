import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    AutomaticOperationComponent,
    AutomaticOperationDetailComponent,
    AutomaticOperationUpdateComponent,
    AutomaticOperationDeletePopupComponent,
    AutomaticOperationDeleteDialogComponent,
    automaticOperationRoute,
    automaticOperationPopupRoute
} from './';

const ENTITY_STATES = [...automaticOperationRoute, ...automaticOperationPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AutomaticOperationComponent,
        AutomaticOperationDetailComponent,
        AutomaticOperationUpdateComponent,
        AutomaticOperationDeleteDialogComponent,
        AutomaticOperationDeletePopupComponent
    ],
    entryComponents: [
        AutomaticOperationComponent,
        AutomaticOperationUpdateComponent,
        AutomaticOperationDeleteDialogComponent,
        AutomaticOperationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaAutomaticOperationModule {}
