import { Moment } from 'moment';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';
import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';
import { StatusSGQ } from 'app/shared/model/enumerations/status-sgq.model';

export interface INaoConformidade {
  id?: number;
  idUsuarioRegistro?: number;
  idUsuarioResponsavel?: number;
  titulo?: string;
  descricao?: any;
  procedente?: boolean;
  causa?: any;
  prazoConclusao?: Moment;
  novoPrazoConclusao?: Moment;
  dataRegistro?: Moment;
  dataConclusao?: Moment;
  analiseFinal?: any;
  statusSGQ?: StatusSGQ;
  acaoSGQS?: IAcaoSGQ[];
  anexos?: IAnexo[];
  auditoria?: IAuditoria;
  resultadoChecklist?: IResultadoChecklist;
}

export class NaoConformidade implements INaoConformidade {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public idUsuarioResponsavel?: number,
    public titulo?: string,
    public descricao?: any,
    public procedente?: boolean,
    public causa?: any,
    public prazoConclusao?: Moment,
    public novoPrazoConclusao?: Moment,
    public dataRegistro?: Moment,
    public dataConclusao?: Moment,
    public analiseFinal?: any,
    public statusSGQ?: StatusSGQ,
    public acaoSGQS?: IAcaoSGQ[],
    public anexos?: IAnexo[],
    public auditoria?: IAuditoria,
    public resultadoChecklist?: IResultadoChecklist
  ) {
    this.procedente = this.procedente || false;
  }
}
