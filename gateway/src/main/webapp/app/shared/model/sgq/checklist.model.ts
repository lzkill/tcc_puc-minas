import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { ISetor } from 'app/shared/model/sgq/setor.model';
import { Periodicidade } from 'app/shared/model/enumerations/periodicidade.model';

export interface IChecklist {
  id?: number;
  titulo?: string;
  periodicidade?: Periodicidade;
  anexos?: IAnexo[];
  items?: IItemChecklist[];
  setor?: ISetor;
}

export class Checklist implements IChecklist {
  constructor(
    public id?: number,
    public titulo?: string,
    public periodicidade?: Periodicidade,
    public anexos?: IAnexo[],
    public items?: IItemChecklist[],
    public setor?: ISetor
  ) {}
}
