import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExternalCode } from 'app/shared/model/external-code.model';
import { ExternalCodeService } from './external-code.service';

@Component({
    selector: 'jhi-external-code-delete-dialog',
    templateUrl: './external-code-delete-dialog.component.html'
})
export class ExternalCodeDeleteDialogComponent {
    externalCode: IExternalCode;

    constructor(
        protected externalCodeService: ExternalCodeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.externalCodeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'externalCodeListModification',
                content: 'Deleted an externalCode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-external-code-delete-popup',
    template: ''
})
export class ExternalCodeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ externalCode }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExternalCodeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.externalCode = externalCode;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/external-code', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/external-code', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
