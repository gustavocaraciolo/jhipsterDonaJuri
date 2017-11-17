import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Processo } from './processo.model';
import { ProcessoService } from './processo.service';

@Component({
    selector: 'jhi-processo-detail',
    templateUrl: './processo-detail.component.html'
})
export class ProcessoDetailComponent implements OnInit, OnDestroy {

    processo: Processo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private processoService: ProcessoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProcessos();
    }

    load(id) {
        this.processoService.find(id).subscribe((processo) => {
            this.processo = processo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProcessos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'processoListModification',
            (response) => this.load(this.processo.id)
        );
    }
}
