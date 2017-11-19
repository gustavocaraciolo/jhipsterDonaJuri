import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Anexo } from './anexo.model';
import { AnexoPopupService } from './anexo-popup.service';
import { AnexoService } from './anexo.service';
import { Processo, ProcessoService } from '../processo';
import { Pendencia, PendenciaService } from '../pendencia';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-anexo-dialog',
    templateUrl: './anexo-dialog.component.html'
})
export class AnexoDialogComponent implements OnInit {

    anexo: Anexo;
    isSaving: boolean;

    processos: Processo[];

    pendencias: Pendencia[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private anexoService: AnexoService,
        private processoService: ProcessoService,
        private pendenciaService: PendenciaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.processoService.query()
            .subscribe((res: ResponseWrapper) => { this.processos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.pendenciaService.query()
            .subscribe((res: ResponseWrapper) => { this.pendencias = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.anexo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.anexoService.update(this.anexo));
        } else {
            this.subscribeToSaveResponse(
                this.anexoService.create(this.anexo));
        }
    }

    private subscribeToSaveResponse(result: Observable<Anexo>) {
        result.subscribe((res: Anexo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Anexo) {
        this.eventManager.broadcast({ name: 'anexoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProcessoById(index: number, item: Processo) {
        return item.id;
    }

    trackPendenciaById(index: number, item: Pendencia) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-anexo-popup',
    template: ''
})
export class AnexoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anexoPopupService: AnexoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.anexoPopupService
                    .open(AnexoDialogComponent as Component, params['id']);
            } else {
                this.anexoPopupService
                    .open(AnexoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
