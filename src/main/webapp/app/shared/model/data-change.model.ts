export interface IDataChange {
    id?: number;
    changeDetail?: string;
}

export class DataChange implements IDataChange {
    constructor(public id?: number, public changeDetail?: string) {}
}
