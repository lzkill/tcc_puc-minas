import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { TipoAcaoSGQ } from 'app/shared/model/enumerations/tipo-acao-sgq.model';
import { StatusSGQ } from 'app/shared/model/enumerations/status-sgq.model';

export interface IAcaoSGQ {
  id?: number;
  idUsuarioRegistro?: number;
  idUsuarioResponsavel?: number;
  tipo?: TipoAcaoSGQ;
  titulo?: string;
  descricao?: any;
  prazoConclusao?: Moment;
  novoPrazoConclusao?: Moment;
  dataRegistro?: Moment;
  dataConclusao?: Moment;
  resultado?: any;
  statusSGQ?: StatusSGQ;
  anexos?: IAnexo[];
  naoConformidade?: INaoConformidade;
}

export class AcaoSGQ implements IAcaoSGQ {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public idUsuarioResponsavel?: number,
    public tipo?: TipoAcaoSGQ,
    public titulo?: string,
    public descricao?: any,
    public prazoConclusao?: Moment,
    public novoPrazoConclusao?: Moment,
    public dataRegistro?: Moment,
    public dataConclusao?: Moment,
    public resultado?: any,
    public statusSGQ?: StatusSGQ,
    public anexos?: IAnexo[],
    public naoConformidade?: INaoConformidade
  ) {}
}
