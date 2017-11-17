import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EscritorioComponent } from './escritorio.component';
import { EscritorioDetailComponent } from './escritorio-detail.component';
import { EscritorioPopupComponent } from './escritorio-dialog.component';
import { EscritorioDeletePopupComponent } from './escritorio-delete-dialog.component';

@Injectable()
export class EscritorioResolvePagingParams implements Resolve<any> {

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

export const escritorioRoute: Routes = [
    {
        path: 'escritorio',
        component: EscritorioComponent,
        resolve: {
            'pagingParams': EscritorioResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.escritorio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'escritorio/:id',
        component: EscritorioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.escritorio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const escritorioPopupRoute: Routes = [
    {
        path: 'escritorio-new',
        component: EscritorioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.escritorio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'escritorio/:id/edit',
        component: EscritorioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.escritorio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'escritorio/:id/delete',
        component: EscritorioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.escritorio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
