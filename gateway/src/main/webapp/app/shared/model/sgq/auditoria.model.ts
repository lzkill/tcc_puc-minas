import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { TipoAuditoria } from 'app/shared/model/enumerations/tipo-auditoria.model';

export interface IAuditoria {
  id?: number;
  tipo?: TipoAuditoria;
  titulo?: string;
  descricao?: any;
  processo?: IProcesso;
}

export class Auditoria implements IAuditoria {
  constructor(
    public id?: number,
    public tipo?: TipoAuditoria,
    public titulo?: string,
    public descricao?: any,
    public processo?: IProcesso
  ) {}
}
