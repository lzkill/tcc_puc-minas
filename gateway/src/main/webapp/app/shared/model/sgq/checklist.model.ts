import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { ISetor } from 'app/shared/model/sgq/setor.model';
import { Periodicidade } from 'app/shared/model/enumerations/periodicidade.model';

export interface IChecklist {
  id?: number;
  titulo?: string;
  periodicidade?: Periodicidade;
  habilitado?: boolean;
  items?: IItemChecklist[];
  anexos?: IAnexo[];
  setor?: ISetor;
}

export class Checklist implements IChecklist {
  constructor(
    public id?: number,
    public titulo?: string,
    public periodicidade?: Periodicidade,
    public habilitado?: boolean,
    public items?: IItemChecklist[],
    public anexos?: IAnexo[],
    public setor?: ISetor
  ) {
    this.habilitado = this.habilitado || false;
  }
}
