export interface IInformativeOperation {
    id?: number;
    comments?: string;
    accountOperationId?: number;
}

export class InformativeOperation implements IInformativeOperation {
    constructor(public id?: number, public comments?: string, public accountOperationId?: number) {}
}
