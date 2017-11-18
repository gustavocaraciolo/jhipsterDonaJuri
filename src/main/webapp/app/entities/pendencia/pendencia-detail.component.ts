import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Pendencia } from './pendencia.model';
import { PendenciaService } from './pendencia.service';

@Component({
    selector: 'jhi-pendencia-detail',
    templateUrl: './pendencia-detail.component.html'
})
export class PendenciaDetailComponent implements OnInit, OnDestroy {

    pendencia: Pendencia;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private pendenciaService: PendenciaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPendencias();
    }

    load(id) {
        this.pendenciaService.find(id).subscribe((pendencia) => {
            this.pendencia = pendencia;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPendencias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pendenciaListModification',
            (response) => this.load(this.pendencia.id)
        );
    }
}
