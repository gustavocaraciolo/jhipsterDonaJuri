import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AnexoComponent } from './anexo.component';
import { AnexoDetailComponent } from './anexo-detail.component';
import { AnexoPopupComponent } from './anexo-dialog.component';
import { AnexoDeletePopupComponent } from './anexo-delete-dialog.component';

@Injectable()
export class AnexoResolvePagingParams implements Resolve<any> {

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

export const anexoRoute: Routes = [
    {
        path: 'anexo',
        component: AnexoComponent,
        resolve: {
            'pagingParams': AnexoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.anexo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'anexo/:id',
        component: AnexoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.anexo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const anexoPopupRoute: Routes = [
    {
        path: 'anexo-new',
        component: AnexoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.anexo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anexo/:id/edit',
        component: AnexoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.anexo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anexo/:id/delete',
        component: AnexoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.anexo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
