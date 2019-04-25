import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInternalCode } from 'app/shared/model/internal-code.model';

@Component({
    selector: 'jhi-internal-code-detail',
    templateUrl: './internal-code-detail.component.html'
})
export class InternalCodeDetailComponent implements OnInit {
    internalCode: IInternalCode;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ internalCode }) => {
            this.internalCode = internalCode;
        });
    }

    previousState() {
        window.history.back();
    }
}
