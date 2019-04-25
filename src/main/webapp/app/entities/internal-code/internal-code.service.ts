import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInternalCode } from 'app/shared/model/internal-code.model';

type EntityResponseType = HttpResponse<IInternalCode>;
type EntityArrayResponseType = HttpResponse<IInternalCode[]>;

@Injectable({ providedIn: 'root' })
export class InternalCodeService {
    public resourceUrl = SERVER_API_URL + 'api/internal-codes';

    constructor(protected http: HttpClient) {}

    create(internalCode: IInternalCode): Observable<EntityResponseType> {
        return this.http.post<IInternalCode>(this.resourceUrl, internalCode, { observe: 'response' });
    }

    update(internalCode: IInternalCode): Observable<EntityResponseType> {
        return this.http.put<IInternalCode>(this.resourceUrl, internalCode, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IInternalCode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInternalCode[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
