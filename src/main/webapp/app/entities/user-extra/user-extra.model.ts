import { BaseEntity } from './../../shared';

export class UserExtra implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public escritorioId?: number,
        public processoAdvogadoCorrenteId?: number,
        public processoClientes?: BaseEntity[],
        public processoAdvogados?: BaseEntity[],
        public pendenciaAdvogados?: BaseEntity[],
    ) {
    }
}
