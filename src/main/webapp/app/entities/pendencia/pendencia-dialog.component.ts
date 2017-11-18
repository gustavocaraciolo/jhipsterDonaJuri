import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Pendencia } from './pendencia.model';
import { PendenciaPopupService } from './pendencia-popup.service';
import { PendenciaService } from './pendencia.service';
import { Processo, ProcessoService } from '../processo';
import { UserExtra, UserExtraService } from '../user-extra';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pendencia-dialog',
    templateUrl: './pendencia-dialog.component.html'
})
export class PendenciaDialogComponent implements OnInit {

    pendencia: Pendencia;
    isSaving: boolean;

    processos: Processo[];

    userextras: UserExtra[];
    dataInicialDp: any;
    dataFinalDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private pendenciaService: PendenciaService,
        private processoService: ProcessoService,
        private userExtraService: UserExtraService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.processoService.query()
            .subscribe((res: ResponseWrapper) => { this.processos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userExtraService.query()
            .subscribe((res: ResponseWrapper) => { this.userextras = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        if (this.pendencia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pendenciaService.update(this.pendencia));
        } else {
            this.subscribeToSaveResponse(
                this.pendenciaService.create(this.pendencia));
        }
    }

    private subscribeToSaveResponse(result: Observable<Pendencia>) {
        result.subscribe((res: Pendencia) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Pendencia) {
        this.eventManager.broadcast({ name: 'pendenciaListModification', content: 'OK'});
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

    trackUserExtraById(index: number, item: UserExtra) {
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
    selector: 'jhi-pendencia-popup',
    template: ''
})
export class PendenciaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pendenciaPopupService: PendenciaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pendenciaPopupService
                    .open(PendenciaDialogComponent as Component, params['id']);
            } else {
                this.pendenciaPopupService
                    .open(PendenciaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
