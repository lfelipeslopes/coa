import { Moment } from 'moment';

export interface IBillingTariff {
    id?: number;
    dayOfWeek?: number;
    startedIn?: Moment;
    value?: number;
    idBillingTariffId?: number;
}

export class BillingTariff implements IBillingTariff {
    constructor(
        public id?: number,
        public dayOfWeek?: number,
        public startedIn?: Moment,
        public value?: number,
        public idBillingTariffId?: number
    ) {}
}
