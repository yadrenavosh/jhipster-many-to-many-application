import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterMtomApplicationSharedModule } from 'app/shared';
import {
    InternalCodeComponent,
    InternalCodeDetailComponent,
    InternalCodeUpdateComponent,
    InternalCodeDeletePopupComponent,
    InternalCodeDeleteDialogComponent,
    internalCodeRoute,
    internalCodePopupRoute
} from './';

const ENTITY_STATES = [...internalCodeRoute, ...internalCodePopupRoute];

@NgModule({
    imports: [JhipsterMtomApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InternalCodeComponent,
        InternalCodeDetailComponent,
        InternalCodeUpdateComponent,
        InternalCodeDeleteDialogComponent,
        InternalCodeDeletePopupComponent
    ],
    entryComponents: [
        InternalCodeComponent,
        InternalCodeUpdateComponent,
        InternalCodeDeleteDialogComponent,
        InternalCodeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterMtomApplicationInternalCodeModule {}
