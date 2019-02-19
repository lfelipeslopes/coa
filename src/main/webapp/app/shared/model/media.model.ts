export interface IMedia {
    id?: number;
    identification?: string;
}

export class Media implements IMedia {
    constructor(public id?: number, public identification?: string) {}
}
