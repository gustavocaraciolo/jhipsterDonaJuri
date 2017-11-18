import { BaseEntity } from './../../shared';

export class UserExtra implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public processoAdvogadoCorrenteId?: number,
        public escritorioId?: number,
        public processoClientes?: BaseEntity[],
        public processoAdvogados?: BaseEntity[],
    ) {
    }
}
