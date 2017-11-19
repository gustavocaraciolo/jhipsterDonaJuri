import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Convite } from './convite.model';
import { ConviteService } from './convite.service';

@Component({
    selector: 'jhi-convite-detail',
    templateUrl: './convite-detail.component.html'
})
export class ConviteDetailComponent implements OnInit, OnDestroy {

    convite: Convite;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private conviteService: ConviteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConvites();
    }

    load(id) {
        this.conviteService.find(id).subscribe((convite) => {
            this.convite = convite;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConvites() {
        this.eventSubscriber = this.eventManager.subscribe(
            'conviteListModification',
            (response) => this.load(this.convite.id)
        );
    }
}
