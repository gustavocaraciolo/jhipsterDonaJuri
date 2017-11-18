import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Escritorio } from './escritorio.model';
import { EscritorioPopupService } from './escritorio-popup.service';
import { EscritorioService } from './escritorio.service';

@Component({
    selector: 'jhi-escritorio-dialog',
    templateUrl: './escritorio-dialog.component.html'
})
export class EscritorioDialogComponent implements OnInit {

    escritorio: Escritorio;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private escritorioService: EscritorioService,
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
        if (this.escritorio.id !== undefined) {
            this.subscribeToSaveResponse(
                this.escritorioService.update(this.escritorio));
        } else {
            this.subscribeToSaveResponse(
                this.escritorioService.create(this.escritorio));
        }
    }

    private subscribeToSaveResponse(result: Observable<Escritorio>) {
        result.subscribe((res: Escritorio) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Escritorio) {
        this.eventManager.broadcast({ name: 'escritorioListModification', content: 'OK'});
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
    selector: 'jhi-escritorio-popup',
    template: ''
})
export class EscritorioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private escritorioPopupService: EscritorioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.escritorioPopupService
                    .open(EscritorioDialogComponent as Component, params['id']);
            } else {
                this.escritorioPopupService
                    .open(EscritorioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
