import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

export interface IPlanoAuditoria {
  id?: number;
  titulo?: string;
  descricao?: any;
  dataInicio?: Moment;
  dataFim?: Moment;
  anexo?: IAnexo;
  items?: IItemPlanoAuditoria[];
}

export class PlanoAuditoria implements IPlanoAuditoria {
  constructor(
    public id?: number,
    public titulo?: string,
    public descricao?: any,
    public dataInicio?: Moment,
    public dataFim?: Moment,
    public anexo?: IAnexo,
    public items?: IItemPlanoAuditoria[]
  ) {}
}
