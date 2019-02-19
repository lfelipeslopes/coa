export const enum AutomaticOperationStatus {
    ACTIVATED = 'ACTIVATED',
    DISABLED = 'DISABLED'
}

export const enum AutomaticEvent {
    RECHARGE = 'RECHARGE',
    BLOCK = 'BLOCK'
}

export const enum TransactionType {
    CREDIT = 'CREDIT',
    DEBIT = 'DEBIT'
}

export interface IAutomaticOperation {
    id?: number;
    amount?: number;
    recurrent?: boolean;
    automatictOperationStatus?: AutomaticOperationStatus;
    automatictEvent?: AutomaticEvent;
    transactionType?: TransactionType;
}

export class AutomaticOperation implements IAutomaticOperation {
    constructor(
        public id?: number,
        public amount?: number,
        public recurrent?: boolean,
        public automatictOperationStatus?: AutomaticOperationStatus,
        public automatictEvent?: AutomaticEvent,
        public transactionType?: TransactionType
    ) {
        this.recurrent = this.recurrent || false;
    }
}
