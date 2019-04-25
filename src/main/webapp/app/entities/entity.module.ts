import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'internal-code',
                loadChildren: './internal-code/internal-code.module#JhipsterMtomApplicationInternalCodeModule'
            },
            {
                path: 'external-code',
                loadChildren: './external-code/external-code.module#JhipsterMtomApplicationExternalCodeModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterMtomApplicationEntityModule {}
