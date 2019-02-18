export const enum TypePerson {
    PRE_PAID = 'PRE_PAID',
    ASSOCIATED = 'ASSOCIATED'
}

export interface IPerson {
    id?: number;
    document?: string;
    name?: string;
    email?: string;
    typePerson?: TypePerson;
}

export class Person implements IPerson {
    constructor(
        public id?: number,
        public document?: string,
        public name?: string,
        public email?: string,
        public typePerson?: TypePerson
    ) {}
}
