export interface IMedia {
    id?: number;
    identification?: string;
    vehicleId?: number;
}

export class Media implements IMedia {
    constructor(public id?: number, public identification?: string, public vehicleId?: number) {}
}
