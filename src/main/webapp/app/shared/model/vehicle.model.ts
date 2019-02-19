export interface IVehicle {
    id?: number;
    color?: string;
    manufacturer?: string;
    model?: string;
    plate?: string;
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
        public idVehicleClassId?: number,
        public idMediaId?: number
    ) {}
}
