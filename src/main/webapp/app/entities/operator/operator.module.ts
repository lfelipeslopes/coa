import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    OperatorComponent,
    OperatorDetailComponent,
    OperatorUpdateComponent,
    OperatorDeletePopupComponent,
    OperatorDeleteDialogComponent,
    operatorRoute,
    operatorPopupRoute
} from './';

const ENTITY_STATES = [...operatorRoute, ...operatorPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OperatorComponent,
        OperatorDetailComponent,
        OperatorUpdateComponent,
        OperatorDeleteDialogComponent,
        OperatorDeletePopupComponent
    ],
    entryComponents: [OperatorComponent, OperatorUpdateComponent, OperatorDeleteDialogComponent, OperatorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaOperatorModule {}
