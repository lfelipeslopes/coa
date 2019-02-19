export interface IFinancialAccount {
    id?: number;
    alias?: string;
    balance?: number;
}

export class FinancialAccount implements IFinancialAccount {
    constructor(public id?: number, public alias?: string, public balance?: number) {}
}
