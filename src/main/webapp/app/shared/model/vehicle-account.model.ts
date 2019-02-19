export interface IVehicleAccount {
    id?: number;
    idVehicleClassId?: number;
}

export class VehicleAccount implements IVehicleAccount {
    constructor(public id?: number, public idVehicleClassId?: number) {}
}
