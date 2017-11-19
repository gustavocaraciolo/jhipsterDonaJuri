import { BaseEntity } from './../../shared';

export class Anexo implements BaseEntity {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public processos?: BaseEntity[],
        public pendencias?: BaseEntity[],
    ) {
    }
}
