import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInternalCode } from 'app/shared/model/internal-code.model';
import { InternalCodeService } from './internal-code.service';
import { IExternalCode } from 'app/shared/model/external-code.model';
import { ExternalCodeService } from 'app/entities/external-code';

@Component({
    selector: 'jhi-internal-code-update',
    templateUrl: './internal-code-update.component.html'
})
export class InternalCodeUpdateComponent implements OnInit {
    internalCode: IInternalCode;
    isSaving: boolean;

    externalcodes: IExternalCode[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected internalCodeService: InternalCodeService,
        protected externalCodeService: ExternalCodeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ internalCode }) => {
            this.internalCode = internalCode;
        });
        this.externalCodeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExternalCode[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExternalCode[]>) => response.body)
            )
            .subscribe((res: IExternalCode[]) => (this.externalcodes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.internalCode.id !== undefined) {
            this.subscribeToSaveResponse(this.internalCodeService.update(this.internalCode));
        } else {
            this.subscribeToSaveResponse(this.internalCodeService.create(this.internalCode));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInternalCode>>) {
        result.subscribe((res: HttpResponse<IInternalCode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackExternalCodeById(index: number, item: IExternalCode) {
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
