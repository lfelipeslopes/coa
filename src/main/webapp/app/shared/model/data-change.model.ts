export interface IDataChange {
    id?: number;
    changeDetail?: string;
    accountOperationId?: number;
}

export class DataChange implements IDataChange {
    constructor(public id?: number, public changeDetail?: string, public accountOperationId?: number) {}
}
