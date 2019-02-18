import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    PassageComponent,
    PassageDetailComponent,
    PassageUpdateComponent,
    PassageDeletePopupComponent,
    PassageDeleteDialogComponent,
    passageRoute,
    passagePopupRoute
} from './';

const ENTITY_STATES = [...passageRoute, ...passagePopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PassageComponent,
        PassageDetailComponent,
        PassageUpdateComponent,
        PassageDeleteDialogComponent,
        PassageDeletePopupComponent
    ],
    entryComponents: [PassageComponent, PassageUpdateComponent, PassageDeleteDialogComponent, PassageDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaPassageModule {}
