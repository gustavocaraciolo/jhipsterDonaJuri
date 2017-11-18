import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pendencia } from './pendencia.model';
import { PendenciaPopupService } from './pendencia-popup.service';
import { PendenciaService } from './pendencia.service';

@Component({
    selector: 'jhi-pendencia-delete-dialog',
    templateUrl: './pendencia-delete-dialog.component.html'
})
export class PendenciaDeleteDialogComponent {

    pendencia: Pendencia;

    constructor(
        private pendenciaService: PendenciaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pendenciaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pendenciaListModification',
                content: 'Deleted an pendencia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pendencia-delete-popup',
    template: ''
})
export class PendenciaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pendenciaPopupService: PendenciaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pendenciaPopupService
                .open(PendenciaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
