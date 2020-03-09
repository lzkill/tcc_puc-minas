import { Moment } from 'moment';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { IConsultoria } from 'app/shared/model/sgq/consultoria.model';
import { IItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';

export interface IAuditoria {
  id?: number;
  idUsuarioRegistro?: number;
  titulo?: string;
  descricao?: any;
  dataRegistro?: Moment;
  dataInicio?: Moment;
  dataFim?: Moment;
  auditor?: string;
  naoConformidades?: INaoConformidade[];
  consultoria?: IConsultoria;
  itemAuditorias?: IItemAuditoria[];
  anexos?: IAnexo[];
}

export class Auditoria implements IAuditoria {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public titulo?: string,
    public descricao?: any,
    public dataRegistro?: Moment,
    public dataInicio?: Moment,
    public dataFim?: Moment,
    public auditor?: string,
    public naoConformidades?: INaoConformidade[],
    public consultoria?: IConsultoria,
    public itemAuditorias?: IItemAuditoria[],
    public anexos?: IAnexo[]
  ) {}
}
