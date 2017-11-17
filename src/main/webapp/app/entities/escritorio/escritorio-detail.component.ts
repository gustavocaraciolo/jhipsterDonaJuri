import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Escritorio } from './escritorio.model';
import { EscritorioService } from './escritorio.service';

@Component({
    selector: 'jhi-escritorio-detail',
    templateUrl: './escritorio-detail.component.html'
})
export class EscritorioDetailComponent implements OnInit, OnDestroy {

    escritorio: Escritorio;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private escritorioService: EscritorioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEscritorios();
    }

    load(id) {
        this.escritorioService.find(id).subscribe((escritorio) => {
            this.escritorio = escritorio;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEscritorios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'escritorioListModification',
            (response) => this.load(this.escritorio.id)
        );
    }
}
