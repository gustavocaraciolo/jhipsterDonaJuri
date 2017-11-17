import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Escritorio } from './escritorio.model';
import { EscritorioPopupService } from './escritorio-popup.service';
import { EscritorioService } from './escritorio.service';

@Component({
    selector: 'jhi-escritorio-delete-dialog',
    templateUrl: './escritorio-delete-dialog.component.html'
})
export class EscritorioDeleteDialogComponent {

    escritorio: Escritorio;

    constructor(
        private escritorioService: EscritorioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.escritorioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'escritorioListModification',
                content: 'Deleted an escritorio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-escritorio-delete-popup',
    template: ''
})
export class EscritorioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private escritorioPopupService: EscritorioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.escritorioPopupService
                .open(EscritorioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
