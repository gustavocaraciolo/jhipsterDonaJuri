import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Convite } from './convite.model';
import { ConvitePopupService } from './convite-popup.service';
import { ConviteService } from './convite.service';

@Component({
    selector: 'jhi-convite-dialog',
    templateUrl: './convite-dialog.component.html'
})
export class ConviteDialogComponent implements OnInit {

    convite: Convite;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private conviteService: ConviteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.convite.id !== undefined) {
            this.subscribeToSaveResponse(
                this.conviteService.update(this.convite));
        } else {
            this.subscribeToSaveResponse(
                this.conviteService.create(this.convite));
        }
    }

    private subscribeToSaveResponse(result: Observable<Convite>) {
        result.subscribe((res: Convite) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Convite) {
        this.eventManager.broadcast({ name: 'conviteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-convite-popup',
    template: ''
})
export class ConvitePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private convitePopupService: ConvitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.convitePopupService
                    .open(ConviteDialogComponent as Component, params['id']);
            } else {
                this.convitePopupService
                    .open(ConviteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
