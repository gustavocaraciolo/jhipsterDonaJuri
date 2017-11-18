import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProcessoComponent } from './processo.component';
import { ProcessoDetailComponent } from './processo-detail.component';
import { ProcessoPopupComponent } from './processo-dialog.component';
import { ProcessoDeletePopupComponent } from './processo-delete-dialog.component';

@Injectable()
export class ProcessoResolvePagingParams implements Resolve<any> {

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

export const processoRoute: Routes = [
    {
        path: 'processo',
        component: ProcessoComponent,
        resolve: {
            'pagingParams': ProcessoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.processo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'processo/:id',
        component: ProcessoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.processo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const processoPopupRoute: Routes = [
    {
        path: 'processo-new',
        component: ProcessoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.processo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'processo/:id/edit',
        component: ProcessoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.processo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'processo/:id/delete',
        component: ProcessoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'discoveryApp.processo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
