import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscoverySharedModule } from '../../shared';
import {
    EscritorioService,
    EscritorioPopupService,
    EscritorioComponent,
    EscritorioDetailComponent,
    EscritorioDialogComponent,
    EscritorioPopupComponent,
    EscritorioDeletePopupComponent,
    EscritorioDeleteDialogComponent,
    escritorioRoute,
    escritorioPopupRoute,
    EscritorioResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...escritorioRoute,
    ...escritorioPopupRoute,
];

@NgModule({
    imports: [
        DiscoverySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EscritorioComponent,
        EscritorioDetailComponent,
        EscritorioDialogComponent,
        EscritorioDeleteDialogComponent,
        EscritorioPopupComponent,
        EscritorioDeletePopupComponent,
    ],
    entryComponents: [
        EscritorioComponent,
        EscritorioDialogComponent,
        EscritorioPopupComponent,
        EscritorioDeleteDialogComponent,
        EscritorioDeletePopupComponent,
    ],
    providers: [
        EscritorioService,
        EscritorioPopupService,
        EscritorioResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryEscritorioModule {}
