import { Moment } from 'moment';
import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { IConsultoria } from 'app/shared/model/sgq/consultoria.model';
import { StatusSolicitacaoAnalise } from 'app/shared/model/enumerations/status-solicitacao-analise.model';

export interface ISolicitacaoAnalise {
  id?: number;
  idUsuarioRegistro?: number;
  dataRegistro?: Moment;
  dataSolicitacao?: Moment;
  idAcompanhamento?: number;
  status?: StatusSolicitacaoAnalise;
  analiseConsultoria?: IAnaliseConsultoria;
  naoConformidade?: INaoConformidade;
  consultoria?: IConsultoria;
}

export class SolicitacaoAnalise implements ISolicitacaoAnalise {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public dataRegistro?: Moment,
    public dataSolicitacao?: Moment,
    public idAcompanhamento?: number,
    public status?: StatusSolicitacaoAnalise,
    public analiseConsultoria?: IAnaliseConsultoria,
    public naoConformidade?: INaoConformidade,
    public consultoria?: IConsultoria
  ) {}
}
