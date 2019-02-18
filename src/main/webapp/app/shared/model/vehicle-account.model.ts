export interface IVehicleAccount {
    id?: number;
    financialAccountId?: number;
    idId?: number;
}

export class VehicleAccount implements IVehicleAccount {
    constructor(public id?: number, public financialAccountId?: number, public idId?: number) {}
}
