import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InternalCode } from 'app/shared/model/internal-code.model';
import { InternalCodeService } from './internal-code.service';
import { InternalCodeComponent } from './internal-code.component';
import { InternalCodeDetailComponent } from './internal-code-detail.component';
import { InternalCodeUpdateComponent } from './internal-code-update.component';
import { InternalCodeDeletePopupComponent } from './internal-code-delete-dialog.component';
import { IInternalCode } from 'app/shared/model/internal-code.model';

@Injectable({ providedIn: 'root' })
export class InternalCodeResolve implements Resolve<IInternalCode> {
    constructor(private service: InternalCodeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInternalCode> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<InternalCode>) => response.ok),
                map((internalCode: HttpResponse<InternalCode>) => internalCode.body)
            );
        }
        return of(new InternalCode());
    }
}

export const internalCodeRoute: Routes = [
    {
        path: '',
        component: InternalCodeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'InternalCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InternalCodeDetailComponent,
        resolve: {
            internalCode: InternalCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InternalCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InternalCodeUpdateComponent,
        resolve: {
            internalCode: InternalCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InternalCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InternalCodeUpdateComponent,
        resolve: {
            internalCode: InternalCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InternalCodes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const internalCodePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: InternalCodeDeletePopupComponent,
        resolve: {
            internalCode: InternalCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InternalCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
