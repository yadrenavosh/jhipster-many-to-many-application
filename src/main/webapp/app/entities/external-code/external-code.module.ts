import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterMtomApplicationSharedModule } from 'app/shared';
import {
    ExternalCodeComponent,
    ExternalCodeDetailComponent,
    ExternalCodeUpdateComponent,
    ExternalCodeDeletePopupComponent,
    ExternalCodeDeleteDialogComponent,
    externalCodeRoute,
    externalCodePopupRoute
} from './';

const ENTITY_STATES = [...externalCodeRoute, ...externalCodePopupRoute];

@NgModule({
    imports: [JhipsterMtomApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExternalCodeComponent,
        ExternalCodeDetailComponent,
        ExternalCodeUpdateComponent,
        ExternalCodeDeleteDialogComponent,
        ExternalCodeDeletePopupComponent
    ],
    entryComponents: [
        ExternalCodeComponent,
        ExternalCodeUpdateComponent,
        ExternalCodeDeleteDialogComponent,
        ExternalCodeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterMtomApplicationExternalCodeModule {}
