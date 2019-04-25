import { IInternalCode } from 'app/shared/model/internal-code.model';

export interface IExternalCode {
    id?: number;
    code?: string;
    description?: string;
    internals?: IInternalCode[];
}

export class ExternalCode implements IExternalCode {
    constructor(public id?: number, public code?: string, public description?: string, public internals?: IInternalCode[]) {}
}
