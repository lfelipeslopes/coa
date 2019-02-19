export interface IInformativeOperation {
    id?: number;
    comments?: string;
}

export class InformativeOperation implements IInformativeOperation {
    constructor(public id?: number, public comments?: string) {}
}
