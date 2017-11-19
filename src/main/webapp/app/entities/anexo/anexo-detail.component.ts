import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Anexo } from './anexo.model';
import { AnexoService } from './anexo.service';

@Component({
    selector: 'jhi-anexo-detail',
    templateUrl: './anexo-detail.component.html'
})
export class AnexoDetailComponent implements OnInit, OnDestroy {

    anexo: Anexo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private anexoService: AnexoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnexos();
    }

    load(id) {
        this.anexoService.find(id).subscribe((anexo) => {
            this.anexo = anexo;
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

    registerChangeInAnexos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'anexoListModification',
            (response) => this.load(this.anexo.id)
        );
    }
}
