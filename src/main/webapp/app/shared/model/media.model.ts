export interface IMedia {
    id?: number;
    identification?: string;
    idMediaId?: number;
}

export class Media implements IMedia {
    constructor(public id?: number, public identification?: string, public idMediaId?: number) {}
}
