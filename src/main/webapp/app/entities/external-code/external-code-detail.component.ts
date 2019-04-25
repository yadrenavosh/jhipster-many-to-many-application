import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExternalCode } from 'app/shared/model/external-code.model';

@Component({
    selector: 'jhi-external-code-detail',
    templateUrl: './external-code-detail.component.html'
})
export class ExternalCodeDetailComponent implements OnInit {
    externalCode: IExternalCode;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ externalCode }) => {
            this.externalCode = externalCode;
        });
    }

    previousState() {
        window.history.back();
    }
}
