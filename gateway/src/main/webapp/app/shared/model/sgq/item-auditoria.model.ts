import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';

export interface IItemAuditoria {
  id?: number;
  titulo?: string;
  descricao?: any;
  habilitado?: boolean;
  processo?: IProcesso;
  anexos?: IAnexo[];
  auditorias?: IAuditoria[];
}

export class ItemAuditoria implements IItemAuditoria {
  constructor(
    public id?: number,
    public titulo?: string,
    public descricao?: any,
    public habilitado?: boolean,
    public processo?: IProcesso,
    public anexos?: IAnexo[],
    public auditorias?: IAuditoria[]
  ) {
    this.habilitado = this.habilitado || false;
  }
}
