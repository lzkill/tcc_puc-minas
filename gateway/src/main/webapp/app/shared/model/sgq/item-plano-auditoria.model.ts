import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';
import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';

export interface IItemPlanoAuditoria {
  id?: number;
  dataInicioPrevisto?: Moment;
  dataFimPrevisto?: Moment;
  anexos?: IAnexo[];
  auditoria?: IAuditoria;
  plano?: IPlanoAuditoria;
}

export class ItemPlanoAuditoria implements IItemPlanoAuditoria {
  constructor(
    public id?: number,
    public dataInicioPrevisto?: Moment,
    public dataFimPrevisto?: Moment,
    public anexos?: IAnexo[],
    public auditoria?: IAuditoria,
    public plano?: IPlanoAuditoria
  ) {}
}
