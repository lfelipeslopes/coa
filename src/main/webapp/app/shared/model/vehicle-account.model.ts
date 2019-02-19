export interface IVehicleAccount {
    id?: number;
    idVehicleId?: number;
}

export class VehicleAccount implements IVehicleAccount {
    constructor(public id?: number, public idVehicleId?: number) {}
}
