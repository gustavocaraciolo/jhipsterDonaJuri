import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Processo } from './processo.model';
import { ProcessoPopupService } from './processo-popup.service';
import { ProcessoService } from './processo.service';

@Component({
    selector: 'jhi-processo-delete-dialog',
    templateUrl: './processo-delete-dialog.component.html'
})
export class ProcessoDeleteDialogComponent {

    processo: Processo;

    constructor(
        private processoService: ProcessoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.processoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'processoListModification',
                content: 'Deleted an processo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-processo-delete-popup',
    template: ''
})
export class ProcessoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private processoPopupService: ProcessoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.processoPopupService
                .open(ProcessoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
