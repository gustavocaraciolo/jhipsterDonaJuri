import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PendenciaComponent } from './pendencia.component';
import { PendenciaDetailComponent } from './pendencia-detail.component';
import { PendenciaPopupComponent } from './pendencia-dialog.component';
import { PendenciaDeletePopupComponent } from './pendencia-delete-dialog.component';

@Injectable()
export class PendenciaResolvePagingParams implements Resolve<any> {

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

export const pendenciaRoute: Routes = [
    {
        path: 'pendencia',
        component: PendenciaComponent,
        resolve: {
            'pagingParams': PendenciaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.pendencia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pendencia/:id',
        component: PendenciaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.pendencia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pendenciaPopupRoute: Routes = [
    {
        path: 'pendencia-new',
        component: PendenciaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.pendencia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pendencia/:id/edit',
        component: PendenciaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.pendencia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pendencia/:id/delete',
        component: PendenciaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.pendencia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
