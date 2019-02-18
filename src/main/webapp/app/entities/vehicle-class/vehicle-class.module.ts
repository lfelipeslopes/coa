import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    VehicleClassComponent,
    VehicleClassDetailComponent,
    VehicleClassUpdateComponent,
    VehicleClassDeletePopupComponent,
    VehicleClassDeleteDialogComponent,
    vehicleClassRoute,
    vehicleClassPopupRoute
} from './';

const ENTITY_STATES = [...vehicleClassRoute, ...vehicleClassPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VehicleClassComponent,
        VehicleClassDetailComponent,
        VehicleClassUpdateComponent,
        VehicleClassDeleteDialogComponent,
        VehicleClassDeletePopupComponent
    ],
    entryComponents: [
        VehicleClassComponent,
        VehicleClassUpdateComponent,
        VehicleClassDeleteDialogComponent,
        VehicleClassDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaVehicleClassModule {}
