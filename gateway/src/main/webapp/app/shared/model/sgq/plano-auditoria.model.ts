import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

export interface IPlanoAuditoria {
  id?: number;
  titulo?: string;
  descricao?: any;
  anexos?: IAnexo[];
  items?: IItemPlanoAuditoria[];
}

export class PlanoAuditoria implements IPlanoAuditoria {
  constructor(
    public id?: number,
    public titulo?: string,
    public descricao?: any,
    public anexos?: IAnexo[],
    public items?: IItemPlanoAuditoria[]
  ) {}
}
