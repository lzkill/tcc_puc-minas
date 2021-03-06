import { IChecklist } from 'app/shared/model/sgq/checklist.model';
import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { IEmpresa } from 'app/shared/model/sgq/empresa.model';

export interface ISetor {
  id?: number;
  nome?: string;
  habilitado?: boolean;
  checklists?: IChecklist[];
  processos?: IProcesso[];
  empresa?: IEmpresa;
}

export class Setor implements ISetor {
  constructor(
    public id?: number,
    public nome?: string,
    public habilitado?: boolean,
    public checklists?: IChecklist[],
    public processos?: IProcesso[],
    public empresa?: IEmpresa
  ) {
    this.habilitado = this.habilitado || false;
  }
}
