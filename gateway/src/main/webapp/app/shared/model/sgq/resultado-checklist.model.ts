import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';

export interface IResultadoChecklist {
  id?: number;
  idUsuarioRegistro?: number;
  titulo?: string;
  dataVerificacao?: Moment;
  anexo?: IAnexo;
  resultadoItems?: IResultadoItemChecklist[];
  checklist?: IChecklist;
}

export class ResultadoChecklist implements IResultadoChecklist {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public titulo?: string,
    public dataVerificacao?: Moment,
    public anexo?: IAnexo,
    public resultadoItems?: IResultadoItemChecklist[],
    public checklist?: IChecklist
  ) {}
}
