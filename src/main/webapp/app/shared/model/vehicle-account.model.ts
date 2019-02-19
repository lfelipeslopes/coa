export const enum VehicleStatus {
    RELEASED = 'RELEASED',
    BLOCKED = 'BLOCKED',
    FORCED_RELEASE = 'FORCED_RELEASE',
    FORCED_LOCK = 'FORCED_LOCK'
}

export interface IVehicleAccount {
    id?: number;
    vehicleStatus?: VehicleStatus;
    idVehicleId?: number;
}

export class VehicleAccount implements IVehicleAccount {
    constructor(public id?: number, public vehicleStatus?: VehicleStatus, public idVehicleId?: number) {}
}
