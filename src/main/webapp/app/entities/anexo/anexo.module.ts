import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscoverySharedModule } from '../../shared';
import {
    AnexoService,
    AnexoPopupService,
    AnexoComponent,
    AnexoDetailComponent,
    AnexoDialogComponent,
    AnexoPopupComponent,
    AnexoDeletePopupComponent,
    AnexoDeleteDialogComponent,
    anexoRoute,
    anexoPopupRoute,
    AnexoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...anexoRoute,
    ...anexoPopupRoute,
];

@NgModule({
    imports: [
        DiscoverySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AnexoComponent,
        AnexoDetailComponent,
        AnexoDialogComponent,
        AnexoDeleteDialogComponent,
        AnexoPopupComponent,
        AnexoDeletePopupComponent,
    ],
    entryComponents: [
        AnexoComponent,
        AnexoDialogComponent,
        AnexoPopupComponent,
        AnexoDeleteDialogComponent,
        AnexoDeletePopupComponent,
    ],
    providers: [
        AnexoService,
        AnexoPopupService,
        AnexoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryAnexoModule {}
