import { ISetor } from 'app/shared/model/sgq/setor.model';

export interface IProcesso {
  id?: number;
  titulo?: string;
  descricao?: any;
  setor?: ISetor;
}

export class Processo implements IProcesso {
  constructor(public id?: number, public titulo?: string, public descricao?: any, public setor?: ISetor) {}
}
