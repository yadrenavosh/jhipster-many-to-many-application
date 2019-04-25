import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExternalCode } from 'app/shared/model/external-code.model';
import { ExternalCodeService } from './external-code.service';
import { IInternalCode } from 'app/shared/model/internal-code.model';
import { InternalCodeService } from 'app/entities/internal-code';

@Component({
    selector: 'jhi-external-code-update',
    templateUrl: './external-code-update.component.html'
})
export class ExternalCodeUpdateComponent implements OnInit {
    externalCode: IExternalCode;
    isSaving: boolean;

    internalcodes: IInternalCode[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected externalCodeService: ExternalCodeService,
        protected internalCodeService: InternalCodeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ externalCode }) => {
            this.externalCode = externalCode;
        });
        this.internalCodeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInternalCode[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInternalCode[]>) => response.body)
            )
            .subscribe((res: IInternalCode[]) => (this.internalcodes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.externalCode.id !== undefined) {
            this.subscribeToSaveResponse(this.externalCodeService.update(this.externalCode));
        } else {
            this.subscribeToSaveResponse(this.externalCodeService.create(this.externalCode));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExternalCode>>) {
        result.subscribe((res: HttpResponse<IExternalCode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackInternalCodeById(index: number, item: IInternalCode) {
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
