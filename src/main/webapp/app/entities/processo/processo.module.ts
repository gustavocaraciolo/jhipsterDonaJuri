import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscoverySharedModule } from '../../shared';
import {
    ProcessoService,
    ProcessoPopupService,
    ProcessoComponent,
    ProcessoDetailComponent,
    ProcessoDialogComponent,
    ProcessoPopupComponent,
    ProcessoDeletePopupComponent,
    ProcessoDeleteDialogComponent,
    processoRoute,
    processoPopupRoute,
    ProcessoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...processoRoute,
    ...processoPopupRoute,
];

@NgModule({
    imports: [
        DiscoverySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProcessoComponent,
        ProcessoDetailComponent,
        ProcessoDialogComponent,
        ProcessoDeleteDialogComponent,
        ProcessoPopupComponent,
        ProcessoDeletePopupComponent,
    ],
    entryComponents: [
        ProcessoComponent,
        ProcessoDialogComponent,
        ProcessoPopupComponent,
        ProcessoDeleteDialogComponent,
        ProcessoDeletePopupComponent,
    ],
    providers: [
        ProcessoService,
        ProcessoPopupService,
        ProcessoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryProcessoModule {}
