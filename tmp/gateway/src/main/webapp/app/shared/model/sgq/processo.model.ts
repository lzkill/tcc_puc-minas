import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { ISetor } from 'app/shared/model/sgq/setor.model';

export interface IProcesso {
  id?: number;
  titulo?: string;
  descricao?: any;
  habilitado?: boolean;
  anexos?: IAnexo[];
  setor?: ISetor;
}

export class Processo implements IProcesso {
  constructor(
    public id?: number,
    public titulo?: string,
    public descricao?: any,
    public habilitado?: boolean,
    public anexos?: IAnexo[],
    public setor?: ISetor
  ) {
    this.habilitado = this.habilitado || false;
  }
}
