import { Moment } from 'moment';
import { IDataChange } from 'app/shared/model/data-change.model';
import { IInformativeOperation } from 'app/shared/model/informative-operation.model';
import { IBalanceCalculation } from 'app/shared/model/balance-calculation.model';
import { IOperator } from 'app/shared/model/operator.model';
import { IAccountTransction } from 'app/shared/model/account-transction.model';
import { IAutomaticOperation } from 'app/shared/model/automatic-operation.model';

export interface IAccountOperation {
    id?: number;
    occurrenceDate?: Moment;
    financialAccountId?: number;
    ids?: IDataChange[];
    ids?: IInformativeOperation[];
    ids?: IBalanceCalculation[];
    ids?: IOperator[];
    ids?: IAccountTransction[];
    ids?: IAutomaticOperation[];
}

export class AccountOperation implements IAccountOperation {
    constructor(
        public id?: number,
        public occurrenceDate?: Moment,
        public financialAccountId?: number,
        public ids?: IDataChange[],
        public ids?: IInformativeOperation[],
        public ids?: IBalanceCalculation[],
        public ids?: IOperator[],
        public ids?: IAccountTransction[],
        public ids?: IAutomaticOperation[]
    ) {}
}
