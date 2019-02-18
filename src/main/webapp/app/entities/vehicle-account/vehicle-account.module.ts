import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoaSharedModule } from 'app/shared';
import {
    VehicleAccountComponent,
    VehicleAccountDetailComponent,
    VehicleAccountUpdateComponent,
    VehicleAccountDeletePopupComponent,
    VehicleAccountDeleteDialogComponent,
    vehicleAccountRoute,
    vehicleAccountPopupRoute
} from './';

const ENTITY_STATES = [...vehicleAccountRoute, ...vehicleAccountPopupRoute];

@NgModule({
    imports: [CoaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VehicleAccountComponent,
        VehicleAccountDetailComponent,
        VehicleAccountUpdateComponent,
        VehicleAccountDeleteDialogComponent,
        VehicleAccountDeletePopupComponent
    ],
    entryComponents: [
        VehicleAccountComponent,
        VehicleAccountUpdateComponent,
        VehicleAccountDeleteDialogComponent,
        VehicleAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaVehicleAccountModule {}
