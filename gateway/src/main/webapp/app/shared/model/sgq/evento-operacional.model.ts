import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { TipoEventoOperacional } from 'app/shared/model/enumerations/tipo-evento-operacional.model';

export interface IEventoOperacional {
  id?: number;
  idUsuarioRegistro?: number;
  tipo?: TipoEventoOperacional;
  titulo?: string;
  descricao?: any;
  dataEvento?: Moment;
  duracao?: number;
  houveParadaProducao?: boolean;
  anexo?: IAnexo;
  processo?: IProcesso;
}

export class EventoOperacional implements IEventoOperacional {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public tipo?: TipoEventoOperacional,
    public titulo?: string,
    public descricao?: any,
    public dataEvento?: Moment,
    public duracao?: number,
    public houveParadaProducao?: boolean,
    public anexo?: IAnexo,
    public processo?: IProcesso
  ) {
    this.houveParadaProducao = this.houveParadaProducao || false;
  }
}
