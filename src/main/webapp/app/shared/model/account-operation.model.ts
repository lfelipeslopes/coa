import { Moment } from 'moment';
import { IOperator } from 'app/shared/model/operator.model';

export interface IAccountOperation {
    id?: number;
    occurrenceDate?: Moment;
    idOperators?: IOperator[];
}

export class AccountOperation implements IAccountOperation {
    constructor(public id?: number, public occurrenceDate?: Moment, public idOperators?: IOperator[]) {}
}
