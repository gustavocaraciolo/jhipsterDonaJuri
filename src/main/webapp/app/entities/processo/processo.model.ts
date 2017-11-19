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
        public clienteId?: number,
        public advogadoCorrenteId?: number,
        public advogados?: BaseEntity[],
        public anexos?: BaseEntity[],
        public pendencias?: BaseEntity[],
    ) {
    }
}
