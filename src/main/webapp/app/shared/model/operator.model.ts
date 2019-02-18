export const enum OperatorStatus {
    ATICVE = 'ATICVE',
    DISABLED = 'DISABLED'
}

export interface IOperator {
    id?: number;
    email?: string;
    name?: string;
    password?: string;
    operatorStatus?: OperatorStatus;
    accountOperationId?: number;
}

export class Operator implements IOperator {
    constructor(
        public id?: number,
        public email?: string,
        public name?: string,
        public password?: string,
        public operatorStatus?: OperatorStatus,
        public accountOperationId?: number
    ) {}
}
