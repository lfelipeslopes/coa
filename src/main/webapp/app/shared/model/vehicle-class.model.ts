export interface IVehicleClass {
    id?: number;
    axes?: number;
    description?: string;
    doubleWheel?: number;
}

export class VehicleClass implements IVehicleClass {
    constructor(public id?: number, public axes?: number, public description?: string, public doubleWheel?: number) {}
}
