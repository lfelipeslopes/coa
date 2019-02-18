import { IMedia } from 'app/shared/model/media.model';
import { IVehicleClass } from 'app/shared/model/vehicle-class.model';
import { IPassage } from 'app/shared/model/passage.model';

export const enum VehicleStatus {
    RELEASED = 'RELEASED',
    BLOCKED = 'BLOCKED',
    FORCED_RELEASE = 'FORCED_RELEASE',
    FORCED_LOCK = 'FORCED_LOCK'
}

export interface IVehicle {
    id?: number;
    color?: string;
    manufacturer?: string;
    model?: string;
    plate?: string;
    vehicleStatus?: VehicleStatus;
    ids?: IMedia[];
    ids?: IVehicleClass[];
    ids?: IPassage[];
}

export class Vehicle implements IVehicle {
    constructor(
        public id?: number,
        public color?: string,
        public manufacturer?: string,
        public model?: string,
        public plate?: string,
        public vehicleStatus?: VehicleStatus,
        public ids?: IMedia[],
        public ids?: IVehicleClass[],
        public ids?: IPassage[]
    ) {}
}
