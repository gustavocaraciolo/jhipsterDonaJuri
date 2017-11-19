import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Anexo } from './anexo.model';
import { AnexoPopupService } from './anexo-popup.service';
import { AnexoService } from './anexo.service';

@Component({
    selector: 'jhi-anexo-delete-dialog',
    templateUrl: './anexo-delete-dialog.component.html'
})
export class AnexoDeleteDialogComponent {

    anexo: Anexo;

    constructor(
        private anexoService: AnexoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.anexoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'anexoListModification',
                content: 'Deleted an anexo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-anexo-delete-popup',
    template: ''
})
export class AnexoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anexoPopupService: AnexoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.anexoPopupService
                .open(AnexoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
