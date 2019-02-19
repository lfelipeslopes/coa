export interface IBillingLocation {
    id?: number;
    description?: string;
    code?: string;
    idBillingLocationId?: number;
    idBillingLocationId?: number;
    idPassageId?: number;
}

export class BillingLocation implements IBillingLocation {
    constructor(
        public id?: number,
        public description?: string,
        public code?: string,
        public idBillingLocationId?: number,
        public idBillingLocationId?: number,
        public idPassageId?: number
    ) {}
}
