import { BaseEntity } from './../../shared';

export const enum Status {
    'PENDENTE',
    'PERDIDO',
    'REALIZADO',
    'REALIZANDO'
}

export class Processo implements BaseEntity {
    constructor(
        public id?: number,
        public numero?: string,
        public status?: Status,
        public parteadversa?: string,
        public advogadoCorrenteId?: number,
        public clienteId?: number,
        public advogados?: BaseEntity[],
        public anexos?: BaseEntity[],
        public pendencias?: BaseEntity[],
    ) {
    }
}
