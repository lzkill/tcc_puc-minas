import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IEmpresa } from 'app/shared/model/sgq/empresa.model';

export interface IProduto {
  id?: number;
  nome?: string;
  descricao?: any;
  anexos?: IAnexo[];
  empresa?: IEmpresa;
}

export class Produto implements IProduto {
  constructor(public id?: number, public nome?: string, public descricao?: any, public anexos?: IAnexo[], public empresa?: IEmpresa) {}
}
