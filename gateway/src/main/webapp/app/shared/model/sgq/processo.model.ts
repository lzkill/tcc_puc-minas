import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { ISetor } from 'app/shared/model/sgq/setor.model';

export interface IProcesso {
  id?: number;
  titulo?: string;
  descricao?: any;
  anexos?: IAnexo[];
  setor?: ISetor;
}

export class Processo implements IProcesso {
  constructor(public id?: number, public titulo?: string, public descricao?: any, public anexos?: IAnexo[], public setor?: ISetor) {}
}
