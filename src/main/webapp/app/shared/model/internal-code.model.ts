import { IExternalCode } from 'app/shared/model/external-code.model';

export interface IInternalCode {
    id?: number;
    code?: string;
    description?: string;
    externals?: IExternalCode[];
}

export class InternalCode implements IInternalCode {
    constructor(public id?: number, public code?: string, public description?: string, public externals?: IExternalCode[]) {}
}
