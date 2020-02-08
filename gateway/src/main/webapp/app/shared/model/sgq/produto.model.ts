import { IEmpresa } from 'app/shared/model/sgq/empresa.model';

export interface IProduto {
  id?: number;
  nome?: string;
  descricao?: any;
  empresa?: IEmpresa;
}

export class Produto implements IProduto {
  constructor(public id?: number, public nome?: string, public descricao?: any, public empresa?: IEmpresa) {}
}
