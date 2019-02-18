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
    idDataChanges?: IDataChange[];
    idInformativeOperations?: IInformativeOperation[];
    idBalanceCalculations?: IBalanceCalculation[];
    idOperators?: IOperator[];
    idAccountTransctions?: IAccountTransction[];
    idAutomaticOperations?: IAutomaticOperation[];
}

export class AccountOperation implements IAccountOperation {
    constructor(
        public id?: number,
        public occurrenceDate?: Moment,
        public financialAccountId?: number,
        public idDataChanges?: IDataChange[],
        public idInformativeOperations?: IInformativeOperation[],
        public idBalanceCalculations?: IBalanceCalculation[],
        public idOperators?: IOperator[],
        public idAccountTransctions?: IAccountTransction[],
        public idAutomaticOperations?: IAutomaticOperation[]
    ) {}
}
