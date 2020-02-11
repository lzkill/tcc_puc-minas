import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IProduto } from 'app/shared/model/sgq/produto.model';
import { ISetor } from 'app/shared/model/sgq/setor.model';

export interface ICampanhaRecall {
  id?: number;
  idUsuarioRegistro?: number;
  titulo?: string;
  descricao?: any;
  dataRegistro?: Moment;
  dataInicio?: Moment;
  dataFim?: Moment;
  resultado?: any;
  anexos?: IAnexo[];
  produto?: IProduto;
  setorResponsavel?: ISetor;
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
    public resultado?: any,
    public anexos?: IAnexo[],
    public produto?: IProduto,
    public setorResponsavel?: ISetor
  ) {}
}
