import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInternalCode } from 'app/shared/model/internal-code.model';
import { InternalCodeService } from './internal-code.service';

@Component({
    selector: 'jhi-internal-code-delete-dialog',
    templateUrl: './internal-code-delete-dialog.component.html'
})
export class InternalCodeDeleteDialogComponent {
    internalCode: IInternalCode;

    constructor(
        protected internalCodeService: InternalCodeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.internalCodeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'internalCodeListModification',
                content: 'Deleted an internalCode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-internal-code-delete-popup',
    template: ''
})
export class InternalCodeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ internalCode }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InternalCodeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.internalCode = internalCode;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/internal-code', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/internal-code', { outlets: { popup: null } }]);
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
