import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Convite } from './convite.model';
import { ConviteService } from './convite.service';

@Injectable()
export class ConvitePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private conviteService: ConviteService

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
                this.conviteService.find(id).subscribe((convite) => {
                    convite.dataEnvio = this.datePipe
                        .transform(convite.dataEnvio, 'yyyy-MM-ddTHH:mm:ss');
                    convite.dataAceitado = this.datePipe
                        .transform(convite.dataAceitado, 'yyyy-MM-ddTHH:mm:ss');
                    convite.dataRejeitado = this.datePipe
                        .transform(convite.dataRejeitado, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.conviteModalRef(component, convite);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.conviteModalRef(component, new Convite());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    conviteModalRef(component: Component, convite: Convite): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.convite = convite;
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
