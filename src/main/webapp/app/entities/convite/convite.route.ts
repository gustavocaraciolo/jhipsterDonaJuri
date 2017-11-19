import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConviteComponent } from './convite.component';
import { ConviteDetailComponent } from './convite-detail.component';
import { ConvitePopupComponent } from './convite-dialog.component';
import { ConviteDeletePopupComponent } from './convite-delete-dialog.component';

@Injectable()
export class ConviteResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const conviteRoute: Routes = [
    {
        path: 'convite',
        component: ConviteComponent,
        resolve: {
            'pagingParams': ConviteResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.convite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'convite/:id',
        component: ConviteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.convite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const convitePopupRoute: Routes = [
    {
        path: 'convite-new',
        component: ConvitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.convite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'convite/:id/edit',
        component: ConvitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.convite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'convite/:id/delete',
        component: ConviteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.convite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
