export interface IBillingLocation {
    id?: number;
    description?: string;
    code?: string;
    idBillingLocationId?: number;
}

export class BillingLocation implements IBillingLocation {
    constructor(public id?: number, public description?: string, public code?: string, public idBillingLocationId?: number) {}
}
