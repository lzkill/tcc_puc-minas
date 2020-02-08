import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';

export interface IResultadoItemChecklist {
  id?: number;
  conforme?: boolean;
  descricao?: any;
  anexo?: IAnexo;
  item?: IItemChecklist;
  resultado?: IResultadoChecklist;
}

export class ResultadoItemChecklist implements IResultadoItemChecklist {
  constructor(
    public id?: number,
    public conforme?: boolean,
    public descricao?: any,
    public anexo?: IAnexo,
    public item?: IItemChecklist,
    public resultado?: IResultadoChecklist
  ) {
    this.conforme = this.conforme || false;
  }
}
