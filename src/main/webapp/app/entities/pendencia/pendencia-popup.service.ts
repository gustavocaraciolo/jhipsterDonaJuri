import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Pendencia } from './pendencia.model';
import { PendenciaService } from './pendencia.service';

@Injectable()
export class PendenciaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pendenciaService: PendenciaService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.pendenciaService.find(id).subscribe((pendencia) => {
                    if (pendencia.dataInicial) {
                        pendencia.dataInicial = {
                            year: pendencia.dataInicial.getFullYear(),
                            month: pendencia.dataInicial.getMonth() + 1,
                            day: pendencia.dataInicial.getDate()
                        };
                    }
                    if (pendencia.dataFinal) {
                        pendencia.dataFinal = {
                            year: pendencia.dataFinal.getFullYear(),
                            month: pendencia.dataFinal.getMonth() + 1,
                            day: pendencia.dataFinal.getDate()
                        };
                    }
                    this.ngbModalRef = this.pendenciaModalRef(component, pendencia);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pendenciaModalRef(component, new Pendencia());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pendenciaModalRef(component: Component, pendencia: Pendencia): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pendencia = pendencia;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
