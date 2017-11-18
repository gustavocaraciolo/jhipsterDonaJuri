import { BaseEntity } from './../../shared';

export class Escritorio implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public telefone?: string,
        public email?: string,
        public userExtraId?: number,
    ) {
    }
}
