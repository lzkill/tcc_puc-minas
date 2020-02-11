import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { IResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';
import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';
import { StatusSGQ } from 'app/shared/model/enumerations/status-sgq.model';

export interface INaoConformidade {
  id?: number;
  idUsuarioRegistro?: number;
  idUsuarioResponsavel?: number;
  titulo?: string;
  fato?: any;
  procedente?: boolean;
  causa?: any;
  prazoConclusao?: Moment;
  novoPrazoConclusao?: Moment;
  dataRegistro?: Moment;
  dataConclusao?: Moment;
  analiseFinal?: any;
  statusSGQ?: StatusSGQ;
  anexos?: IAnexo[];
  acaoSGQS?: IAcaoSGQ[];
  resultadoAuditoria?: IResultadoAuditoria;
  resultadoItemChecklist?: IResultadoItemChecklist;
}

export class NaoConformidade implements INaoConformidade {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public idUsuarioResponsavel?: number,
    public titulo?: string,
    public fato?: any,
    public procedente?: boolean,
    public causa?: any,
    public prazoConclusao?: Moment,
    public novoPrazoConclusao?: Moment,
    public dataRegistro?: Moment,
    public dataConclusao?: Moment,
    public analiseFinal?: any,
    public statusSGQ?: StatusSGQ,
    public anexos?: IAnexo[],
    public acaoSGQS?: IAcaoSGQ[],
    public resultadoAuditoria?: IResultadoAuditoria,
    public resultadoItemChecklist?: IResultadoItemChecklist
  ) {
    this.procedente = this.procedente || false;
  }
}
