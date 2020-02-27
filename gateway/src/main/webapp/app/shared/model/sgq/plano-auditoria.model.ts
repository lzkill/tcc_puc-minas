import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';

export interface IPlanoAuditoria {
  id?: number;
  titulo?: string;
  descricao?: any;
  habilitado?: boolean;
  items?: IItemPlanoAuditoria[];
  anexos?: IAnexo[];
}

export class PlanoAuditoria implements IPlanoAuditoria {
  constructor(
    public id?: number,
    public titulo?: string,
    public descricao?: any,
    public habilitado?: boolean,
    public items?: IItemPlanoAuditoria[],
    public anexos?: IAnexo[]
  ) {
    this.habilitado = this.habilitado || false;
  }
}
