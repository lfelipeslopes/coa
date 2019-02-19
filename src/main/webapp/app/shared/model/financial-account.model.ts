export const enum FinancialAccountStatus {
    ENABLED = 'ENABLED',
    DISABLED = 'DISABLED'
}

export interface IFinancialAccount {
    id?: number;
    alias?: string;
    balance?: number;
    financialAccountStatus?: FinancialAccountStatus;
}

export class FinancialAccount implements IFinancialAccount {
    constructor(
        public id?: number,
        public alias?: string,
        public balance?: number,
        public financialAccountStatus?: FinancialAccountStatus
    ) {}
}
