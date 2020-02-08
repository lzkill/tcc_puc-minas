import { IProduto } from 'app/shared/model/sgq/produto.model';
import { ISetor } from 'app/shared/model/sgq/setor.model';

export interface IEmpresa {
  id?: number;
  nome?: string;
  produtos?: IProduto[];
  setors?: ISetor[];
}

export class Empresa implements IEmpresa {
  constructor(public id?: number, public nome?: string, public produtos?: IProduto[], public setors?: ISetor[]) {}
}
