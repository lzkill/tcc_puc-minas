import { Moment } from 'moment';
import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';

export interface IResultadoChecklist {
  id?: number;
  idUsuarioRegistro?: number;
  dataRegistro?: Moment;
  dataVerificacao?: Moment;
  resultadoItems?: IResultadoItemChecklist[];
  naoConformidades?: INaoConformidade[];
  produtoNaoConformes?: IProdutoNaoConforme[];
  checklist?: IChecklist;
  anexos?: IAnexo[];
}

export class ResultadoChecklist implements IResultadoChecklist {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public dataRegistro?: Moment,
    public dataVerificacao?: Moment,
    public resultadoItems?: IResultadoItemChecklist[],
    public naoConformidades?: INaoConformidade[],
    public produtoNaoConformes?: IProdutoNaoConforme[],
    public checklist?: IChecklist,
    public anexos?: IAnexo[]
  ) {}
}
