import { Moment } from 'moment';
import { IAddress } from 'app/shared/model//address.model';

export interface INominee {
    id?: number;
    nomineeName?: string;
    relationShip?: string;
    dateOfBirth?: Moment;
    guardianName?: string;
    addresses?: IAddress[];
    applicationProspectId?: number;
}

export class Nominee implements INominee {
    constructor(
        public id?: number,
        public nomineeName?: string,
        public relationShip?: string,
        public dateOfBirth?: Moment,
        public guardianName?: string,
        public addresses?: IAddress[],
        public applicationProspectId?: number
    ) {}
}
