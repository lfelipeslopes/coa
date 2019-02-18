export const enum TransactionType {
    CREDIT = 'CREDIT',
    DEBIT = 'DEBIT'
}

export interface IAccountTransction {
    id?: number;
    amount?: number;
    origin?: string;
    transactionType?: TransactionType;
    accountOperationId?: number;
}

export class AccountTransction implements IAccountTransction {
    constructor(
        public id?: number,
        public amount?: number,
        public origin?: string,
        public transactionType?: TransactionType,
        public accountOperationId?: number
    ) {}
}
