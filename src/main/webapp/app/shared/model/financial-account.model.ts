import { IUserAccount } from 'app/shared/model/user-account.model';
import { IVehicleAccount } from 'app/shared/model/vehicle-account.model';
import { IAccountOperation } from 'app/shared/model/account-operation.model';

export interface IFinancialAccount {
    id?: number;
    alias?: string;
    balance?: number;
    idUserAccounts?: IUserAccount[];
    idVehicleAccounts?: IVehicleAccount[];
    idAccountOperations?: IAccountOperation[];
}

export class FinancialAccount implements IFinancialAccount {
    constructor(
        public id?: number,
        public alias?: string,
        public balance?: number,
        public idUserAccounts?: IUserAccount[],
        public idVehicleAccounts?: IVehicleAccount[],
        public idAccountOperations?: IAccountOperation[]
    ) {}
}
