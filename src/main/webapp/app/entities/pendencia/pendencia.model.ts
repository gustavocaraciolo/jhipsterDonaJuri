import { BaseEntity } from './../../shared';

export const enum Status {
    'PENDENTE',
    'PERDIDO',
    'REALIZADO',
    'REALIZANDO'
}

export class Pendencia implements BaseEntity {
    constructor(
        public id?: number,
        public providencia?: string,
        public observacoes?: any,
        public dataInicial?: any,
        public dataFinal?: any,
        public status?: Status,
        public processoId?: number,
        public advogados?: BaseEntity[],
        public anexos?: BaseEntity[],
    ) {
    }
}
