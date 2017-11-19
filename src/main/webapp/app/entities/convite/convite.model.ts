import { BaseEntity } from './../../shared';

export class Convite implements BaseEntity {
    constructor(
        public id?: number,
        public email?: string,
        public dataEnvio?: any,
        public dataAceitado?: any,
        public dataRejeitado?: any,
        public escritorios?: BaseEntity[],
        public advogados?: BaseEntity[],
    ) {
    }
}
