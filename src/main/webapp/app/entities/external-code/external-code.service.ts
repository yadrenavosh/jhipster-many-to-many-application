import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExternalCode } from 'app/shared/model/external-code.model';

type EntityResponseType = HttpResponse<IExternalCode>;
type EntityArrayResponseType = HttpResponse<IExternalCode[]>;

@Injectable({ providedIn: 'root' })
export class ExternalCodeService {
    public resourceUrl = SERVER_API_URL + 'api/external-codes';

    constructor(protected http: HttpClient) {}

    create(externalCode: IExternalCode): Observable<EntityResponseType> {
        return this.http.post<IExternalCode>(this.resourceUrl, externalCode, { observe: 'response' });
    }

    update(externalCode: IExternalCode): Observable<EntityResponseType> {
        return this.http.put<IExternalCode>(this.resourceUrl, externalCode, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExternalCode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExternalCode[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
