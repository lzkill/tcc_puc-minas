import { Moment } from 'moment';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { IConsultoria } from 'app/shared/model/sgq/consultoria.model';
import { StatusSolicitacaoAnalise } from 'app/shared/model/enumerations/status-solicitacao-analise.model';

export interface ISolicitacaoAnalise {
  id?: number;
  idUsuarioRegistro?: number;
  dataRegistro?: Moment;
  dataSolicitacao?: Moment;
  uuid?: string;
  status?: StatusSolicitacaoAnalise;
  naoConformidade?: INaoConformidade;
  analiseConsultoria?: IAnaliseConsultoria;
  consultoria?: IConsultoria;
}

export class SolicitacaoAnalise implements ISolicitacaoAnalise {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public dataRegistro?: Moment,
    public dataSolicitacao?: Moment,
    public uuid?: string,
    public status?: StatusSolicitacaoAnalise,
    public naoConformidade?: INaoConformidade,
    public analiseConsultoria?: IAnaliseConsultoria,
    public consultoria?: IConsultoria
  ) {}
}
