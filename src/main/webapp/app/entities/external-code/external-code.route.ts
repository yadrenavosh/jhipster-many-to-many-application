import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExternalCode } from 'app/shared/model/external-code.model';
import { ExternalCodeService } from './external-code.service';
import { ExternalCodeComponent } from './external-code.component';
import { ExternalCodeDetailComponent } from './external-code-detail.component';
import { ExternalCodeUpdateComponent } from './external-code-update.component';
import { ExternalCodeDeletePopupComponent } from './external-code-delete-dialog.component';
import { IExternalCode } from 'app/shared/model/external-code.model';

@Injectable({ providedIn: 'root' })
export class ExternalCodeResolve implements Resolve<IExternalCode> {
    constructor(private service: ExternalCodeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExternalCode> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExternalCode>) => response.ok),
                map((externalCode: HttpResponse<ExternalCode>) => externalCode.body)
            );
        }
        return of(new ExternalCode());
    }
}

export const externalCodeRoute: Routes = [
    {
        path: '',
        component: ExternalCodeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ExternalCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExternalCodeDetailComponent,
        resolve: {
            externalCode: ExternalCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExternalCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExternalCodeUpdateComponent,
        resolve: {
            externalCode: ExternalCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExternalCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExternalCodeUpdateComponent,
        resolve: {
            externalCode: ExternalCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExternalCodes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const externalCodePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExternalCodeDeletePopupComponent,
        resolve: {
            externalCode: ExternalCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExternalCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
