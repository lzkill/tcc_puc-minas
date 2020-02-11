import { Moment } from 'moment';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IProduto } from 'app/shared/model/sgq/produto.model';
import { IResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';
import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';
import { StatusSGQ } from 'app/shared/model/enumerations/status-sgq.model';

export interface IProdutoNaoConforme {
  id?: number;
  idUsuarioRegistro?: number;
  idUsuarioResponsavel?: number;
  titulo?: string;
  descricao?: any;
  procedente?: boolean;
  dataRegistro?: Moment;
  analiseFinal?: any;
  statusSGQ?: StatusSGQ;
  acao?: IAcaoSGQ;
  naoConformidade?: INaoConformidade;
  anexos?: IAnexo[];
  produto?: IProduto;
  resultadoAuditoria?: IResultadoAuditoria;
  resultadoItemChecklist?: IResultadoItemChecklist;
}

export class ProdutoNaoConforme implements IProdutoNaoConforme {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public idUsuarioResponsavel?: number,
    public titulo?: string,
    public descricao?: any,
    public procedente?: boolean,
    public dataRegistro?: Moment,
    public analiseFinal?: any,
    public statusSGQ?: StatusSGQ,
    public acao?: IAcaoSGQ,
    public naoConformidade?: INaoConformidade,
    public anexos?: IAnexo[],
    public produto?: IProduto,
    public resultadoAuditoria?: IResultadoAuditoria,
    public resultadoItemChecklist?: IResultadoItemChecklist
  ) {
    this.procedente = this.procedente || false;
  }
}
