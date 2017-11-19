import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Convite } from './convite.model';
import { ConvitePopupService } from './convite-popup.service';
import { ConviteService } from './convite.service';

@Component({
    selector: 'jhi-convite-delete-dialog',
    templateUrl: './convite-delete-dialog.component.html'
})
export class ConviteDeleteDialogComponent {

    convite: Convite;

    constructor(
        private conviteService: ConviteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.conviteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'conviteListModification',
                content: 'Deleted an convite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-convite-delete-popup',
    template: ''
})
export class ConviteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private convitePopupService: ConvitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.convitePopupService
                .open(ConviteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
