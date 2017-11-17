import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DiscoveryEscritorioModule } from './escritorio/escritorio.module';
import { DiscoveryUserExtraModule } from './user-extra/user-extra.module';
import { DiscoveryProcessoModule } from './processo/processo.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        DiscoveryEscritorioModule,
        DiscoveryUserExtraModule,
        DiscoveryProcessoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryEntityModule {}
