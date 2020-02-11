import { Moment } from 'moment';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IEmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';
import { StatusAnaliseExterna } from 'app/shared/model/enumerations/status-analise-externa.model';

export interface IAnaliseConsultoria {
  id?: number;
  dataSolicitacaoAnalise?: Moment;
  dataAnalise?: Moment;
  descricao?: any;
  responsavelAnalise?: string;
  status?: StatusAnaliseExterna;
  acao?: IAcaoSGQ;
  anexos?: IAnexo[];
  empresa?: IEmpresaConsultoria;
}

export class AnaliseConsultoria implements IAnaliseConsultoria {
  constructor(
    public id?: number,
    public dataSolicitacaoAnalise?: Moment,
    public dataAnalise?: Moment,
    public descricao?: any,
    public responsavelAnalise?: string,
    public status?: StatusAnaliseExterna,
    public acao?: IAcaoSGQ,
    public anexos?: IAnexo[],
    public empresa?: IEmpresaConsultoria
  ) {}
}
