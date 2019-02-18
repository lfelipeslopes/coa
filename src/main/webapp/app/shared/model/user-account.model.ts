export interface IUserAccount {
    id?: number;
    financialAccountId?: number;
    accountableId?: number;
    contactsId?: number;
}

export class UserAccount implements IUserAccount {
    constructor(public id?: number, public financialAccountId?: number, public accountableId?: number, public contactsId?: number) {}
}
