import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { ICategoriaNorma } from 'app/shared/model/sgq/categoria-norma.model';

export interface INorma {
  id?: number;
  orgao?: string;
  titulo?: string;
  descricao?: any;
  versao?: string;
  numeroEdicao?: number;
  dataEdicao?: Moment;
  dataInicioValidade?: Moment;
  anexo?: IAnexo;
  categorias?: ICategoriaNorma[];
}

export class Norma implements INorma {
  constructor(
    public id?: number,
    public orgao?: string,
    public titulo?: string,
    public descricao?: any,
    public versao?: string,
    public numeroEdicao?: number,
    public dataEdicao?: Moment,
    public dataInicioValidade?: Moment,
    public anexo?: IAnexo,
    public categorias?: ICategoriaNorma[]
  ) {}
}
