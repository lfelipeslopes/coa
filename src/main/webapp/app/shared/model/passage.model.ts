import { Moment } from 'moment';

export interface IPassage {
    id?: number;
    amount?: number;
    automaticPassage?: boolean;
    lane?: number;
    occurrenceDate?: Moment;
    passageIdentification?: number;
    plate?: string;
    processedAt?: Moment;
    vehicleId?: number;
    billingLocationId?: number;
    classifiedClassId?: number;
    detectClassId?: number;
    chargedClassId?: number;
}

export class Passage implements IPassage {
    constructor(
        public id?: number,
        public amount?: number,
        public automaticPassage?: boolean,
        public lane?: number,
        public occurrenceDate?: Moment,
        public passageIdentification?: number,
        public plate?: string,
        public processedAt?: Moment,
        public vehicleId?: number,
        public billingLocationId?: number,
        public classifiedClassId?: number,
        public detectClassId?: number,
        public chargedClassId?: number
    ) {
        this.automaticPassage = this.automaticPassage || false;
    }
}
