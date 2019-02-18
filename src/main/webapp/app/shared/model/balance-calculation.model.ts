export interface IBalanceCalculation {
    id?: number;
    balance?: number;
    accountOperationId?: number;
}

export class BalanceCalculation implements IBalanceCalculation {
    constructor(public id?: number, public balance?: number, public accountOperationId?: number) {}
}
