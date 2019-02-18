export interface IBillingLocation {
    id?: number;
    description?: string;
    code?: string;
    idId?: number;
}

export class BillingLocation implements IBillingLocation {
    constructor(public id?: number, public description?: string, public code?: string, public idId?: number) {}
}
