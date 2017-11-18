import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscoverySharedModule } from '../../shared';
import {
    PendenciaService,
    PendenciaPopupService,
    PendenciaComponent,
    PendenciaDetailComponent,
    PendenciaDialogComponent,
    PendenciaPopupComponent,
    PendenciaDeletePopupComponent,
    PendenciaDeleteDialogComponent,
    pendenciaRoute,
    pendenciaPopupRoute,
    PendenciaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pendenciaRoute,
    ...pendenciaPopupRoute,
];

@NgModule({
    imports: [
        DiscoverySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PendenciaComponent,
        PendenciaDetailComponent,
        PendenciaDialogComponent,
        PendenciaDeleteDialogComponent,
        PendenciaPopupComponent,
        PendenciaDeletePopupComponent,
    ],
    entryComponents: [
        PendenciaComponent,
        PendenciaDialogComponent,
        PendenciaPopupComponent,
        PendenciaDeleteDialogComponent,
        PendenciaDeletePopupComponent,
    ],
    providers: [
        PendenciaService,
        PendenciaPopupService,
        PendenciaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryPendenciaModule {}
