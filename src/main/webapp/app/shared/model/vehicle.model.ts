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
    idVehicleClassId?: number;
    idMediaId?: number;
}

export class Vehicle implements IVehicle {
    constructor(
        public id?: number,
        public color?: string,
        public manufacturer?: string,
        public model?: string,
        public plate?: string,
        public vehicleStatus?: VehicleStatus,
        public idVehicleClassId?: number,
        public idMediaId?: number
    ) {}
}
