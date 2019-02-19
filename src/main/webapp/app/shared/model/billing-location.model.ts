import { IBillingTariff } from 'app/shared/model/billing-tariff.model';
import { IPassage } from 'app/shared/model/passage.model';

export interface IBillingLocation {
    id?: number;
    description?: string;
    code?: string;
    idBillingLocations?: IBillingTariff[];
    idBillingLocations?: IPassage[];
}

export class BillingLocation implements IBillingLocation {
    constructor(
        public id?: number,
        public description?: string,
        public code?: string,
        public idBillingLocations?: IBillingTariff[],
        public idBillingLocations?: IPassage[]
    ) {}
}
