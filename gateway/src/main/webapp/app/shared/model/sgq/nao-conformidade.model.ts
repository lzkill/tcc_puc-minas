import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { StatusSGQ } from 'app/shared/model/enumerations/status-sgq.model';

export interface INaoConformidade {
  id?: number;
  idUsuarioRegistro?: number;
  idUsuarioResponsavel?: number;
  titulo?: string;
  fato?: any;
  procedente?: boolean;
  causa?: any;
  prazoConclusao?: Moment;
  novoPrazoConclusao?: Moment;
  dataRegistro?: Moment;
  dataConclusao?: Moment;
  analiseFinal?: any;
  statusSGQ?: StatusSGQ;
  anexo?: IAnexo;
  acaos?: IAcaoSGQ[];
}

export class NaoConformidade implements INaoConformidade {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public idUsuarioResponsavel?: number,
    public titulo?: string,
    public fato?: any,
    public procedente?: boolean,
    public causa?: any,
    public prazoConclusao?: Moment,
    public novoPrazoConclusao?: Moment,
    public dataRegistro?: Moment,
    public dataConclusao?: Moment,
    public analiseFinal?: any,
    public statusSGQ?: StatusSGQ,
    public anexo?: IAnexo,
    public acaos?: IAcaoSGQ[]
  ) {
    this.procedente = this.procedente || false;
  }
}
