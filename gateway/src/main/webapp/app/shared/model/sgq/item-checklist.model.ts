import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';

export interface IItemChecklist {
  id?: number;
  ordem?: number;
  titulo?: string;
  descricao?: any;
  anexos?: IAnexo[];
  checklist?: IChecklist;
}

export class ItemChecklist implements IItemChecklist {
  constructor(
    public id?: number,
    public ordem?: number,
    public titulo?: string,
    public descricao?: any,
    public anexos?: IAnexo[],
    public checklist?: IChecklist
  ) {}
}
