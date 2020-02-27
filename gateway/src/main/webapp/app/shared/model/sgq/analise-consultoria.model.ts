import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { ISolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';
import { StatusAprovacao } from 'app/shared/model/enumerations/status-aprovacao.model';

export interface IAnaliseConsultoria {
  id?: number;
  dataAnalise?: Moment;
  conteudo?: any;
  responsavel?: string;
  status?: StatusAprovacao;
  anexos?: IAnexo[];
  solicitacaoAnalise?: ISolicitacaoAnalise;
}

export class AnaliseConsultoria implements IAnaliseConsultoria {
  constructor(
    public id?: number,
    public dataAnalise?: Moment,
    public conteudo?: any,
    public responsavel?: string,
    public status?: StatusAprovacao,
    public anexos?: IAnexo[],
    public solicitacaoAnalise?: ISolicitacaoAnalise
  ) {}
}
