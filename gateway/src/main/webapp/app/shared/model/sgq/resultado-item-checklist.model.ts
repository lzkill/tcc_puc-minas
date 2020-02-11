import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';

export interface IResultadoItemChecklist {
  id?: number;
  conforme?: boolean;
  descricao?: any;
  anexos?: IAnexo[];
  naoConformidades?: INaoConformidade[];
  produtoNaoConformes?: IProdutoNaoConforme[];
  item?: IItemChecklist;
  resultado?: IResultadoChecklist;
}

export class ResultadoItemChecklist implements IResultadoItemChecklist {
  constructor(
    public id?: number,
    public conforme?: boolean,
    public descricao?: any,
    public anexos?: IAnexo[],
    public naoConformidades?: INaoConformidade[],
    public produtoNaoConformes?: IProdutoNaoConforme[],
    public item?: IItemChecklist,
    public resultado?: IResultadoChecklist
  ) {
    this.conforme = this.conforme || false;
  }
}
