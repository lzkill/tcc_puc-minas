import { Moment } from 'moment';
import { IItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { ModalidadeAuditoria } from 'app/shared/model/enumerations/modalidade-auditoria.model';

export interface IItemPlanoAuditoria {
  id?: number;
  titulo?: string;
  descricao?: any;
  modalidade?: ModalidadeAuditoria;
  dataInicioPrevisto?: Moment;
  dataFimPrevisto?: Moment;
  itemAuditoria?: IItemAuditoria;
  anexos?: IAnexo[];
  plano?: IPlanoAuditoria;
}

export class ItemPlanoAuditoria implements IItemPlanoAuditoria {
  constructor(
    public id?: number,
    public titulo?: string,
    public descricao?: any,
    public modalidade?: ModalidadeAuditoria,
    public dataInicioPrevisto?: Moment,
    public dataFimPrevisto?: Moment,
    public itemAuditoria?: IItemAuditoria,
    public anexos?: IAnexo[],
    public plano?: IPlanoAuditoria
  ) {}
}
