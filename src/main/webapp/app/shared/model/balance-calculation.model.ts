export interface IBalanceCalculation {
    id?: number;
    balance?: number;
}

export class BalanceCalculation implements IBalanceCalculation {
    constructor(public id?: number, public balance?: number) {}
}
