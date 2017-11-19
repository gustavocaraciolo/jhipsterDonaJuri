import { BaseEntity } from './../../shared';

export class UserExtra implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public escritorioId?: number,
        public processoClientes?: BaseEntity[],
        public processoAdvogadoCorrentes?: BaseEntity[],
        public processoAdvogados?: BaseEntity[],
        public pendenciaAdvogados?: BaseEntity[],
    ) {
    }
}
