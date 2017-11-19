import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscoverySharedModule } from '../../shared';
import {
    ConviteService,
    ConvitePopupService,
    ConviteComponent,
    ConviteDetailComponent,
    ConviteDialogComponent,
    ConvitePopupComponent,
    ConviteDeletePopupComponent,
    ConviteDeleteDialogComponent,
    conviteRoute,
    convitePopupRoute,
    ConviteResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...conviteRoute,
    ...convitePopupRoute,
];

@NgModule({
    imports: [
        DiscoverySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ConviteComponent,
        ConviteDetailComponent,
        ConviteDialogComponent,
        ConviteDeleteDialogComponent,
        ConvitePopupComponent,
        ConviteDeletePopupComponent,
    ],
    entryComponents: [
        ConviteComponent,
        ConviteDialogComponent,
        ConvitePopupComponent,
        ConviteDeleteDialogComponent,
        ConviteDeletePopupComponent,
    ],
    providers: [
        ConviteService,
        ConvitePopupService,
        ConviteResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryConviteModule {}
