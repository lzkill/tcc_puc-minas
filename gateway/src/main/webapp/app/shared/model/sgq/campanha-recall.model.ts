import { Moment } from 'moment';
import { IProduto } from 'app/shared/model/sgq/produto.model';
import { ISetor } from 'app/shared/model/sgq/setor.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { StatusPublicacao } from 'app/shared/model/enumerations/status-publicacao.model';

export interface ICampanhaRecall {
  id?: number;
  idUsuarioRegistro?: number;
  titulo?: string;
  descricao?: any;
  dataRegistro?: Moment;
  dataInicio?: Moment;
  dataFim?: Moment;
  dataPublicacao?: Moment;
  status?: StatusPublicacao;
  produto?: IProduto;
  setorResponsavel?: ISetor;
  anexos?: IAnexo[];
}

export class CampanhaRecall implements ICampanhaRecall {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public titulo?: string,
    public descricao?: any,
    public dataRegistro?: Moment,
    public dataInicio?: Moment,
    public dataFim?: Moment,
    public dataPublicacao?: Moment,
    public status?: StatusPublicacao,
    public produto?: IProduto,
    public setorResponsavel?: ISetor,
    public anexos?: IAnexo[]
  ) {}
}
