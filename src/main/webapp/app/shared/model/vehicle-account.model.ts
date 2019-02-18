export interface IVehicleAccount {
    id?: number;
    financialAccountId?: number;
    idVehicleClassId?: number;
}

export class VehicleAccount implements IVehicleAccount {
    constructor(public id?: number, public financialAccountId?: number, public idVehicleClassId?: number) {}
}
