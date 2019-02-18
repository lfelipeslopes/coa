import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    DataChangeComponent,
    DataChangeDetailComponent,
    DataChangeUpdateComponent,
    DataChangeDeletePopupComponent,
    DataChangeDeleteDialogComponent,
    dataChangeRoute,
    dataChangePopupRoute
} from './';

const ENTITY_STATES = [...dataChangeRoute, ...dataChangePopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DataChangeComponent,
        DataChangeDetailComponent,
        DataChangeUpdateComponent,
        DataChangeDeleteDialogComponent,
        DataChangeDeletePopupComponent
    ],
    entryComponents: [DataChangeComponent, DataChangeUpdateComponent, DataChangeDeleteDialogComponent, DataChangeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaDataChangeModule {}
