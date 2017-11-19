import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Processo } from './processo.model';
import { ProcessoPopupService } from './processo-popup.service';
import { ProcessoService } from './processo.service';
import { UserExtra, UserExtraService } from '../user-extra';
import { Anexo, AnexoService } from '../anexo';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-processo-dialog',
    templateUrl: './processo-dialog.component.html'
})
export class ProcessoDialogComponent implements OnInit {

    processo: Processo;
    isSaving: boolean;

    advogadocorrentes: UserExtra[];

    userextras: UserExtra[];

    anexos: Anexo[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private processoService: ProcessoService,
        private userExtraService: UserExtraService,
        private anexoService: AnexoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userExtraService
            .query({filter: 'processoadvogadocorrente-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.processo.advogadoCorrenteId) {
                    this.advogadocorrentes = res.json;
                } else {
                    this.userExtraService
                        .find(this.processo.advogadoCorrenteId)
                        .subscribe((subRes: UserExtra) => {
                            this.advogadocorrentes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.userExtraService.query()
            .subscribe((res: ResponseWrapper) => { this.userextras = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.anexoService.query()
            .subscribe((res: ResponseWrapper) => { this.anexos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.processo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.processoService.update(this.processo));
        } else {
            this.subscribeToSaveResponse(
                this.processoService.create(this.processo));
        }
    }

    private subscribeToSaveResponse(result: Observable<Processo>) {
        result.subscribe((res: Processo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Processo) {
        this.eventManager.broadcast({ name: 'processoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserExtraById(index: number, item: UserExtra) {
        return item.id;
    }

    trackAnexoById(index: number, item: Anexo) {
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
    selector: 'jhi-processo-popup',
    template: ''
})
export class ProcessoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private processoPopupService: ProcessoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.processoPopupService
                    .open(ProcessoDialogComponent as Component, params['id']);
            } else {
                this.processoPopupService
                    .open(ProcessoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
