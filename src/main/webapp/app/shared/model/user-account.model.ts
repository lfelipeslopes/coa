export interface IUserAccount {
    id?: number;
    accountableId?: number;
    contactsId?: number;
}

export class UserAccount implements IUserAccount {
    constructor(public id?: number, public accountableId?: number, public contactsId?: number) {}
}
