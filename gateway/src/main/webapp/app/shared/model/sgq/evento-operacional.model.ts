import { Moment } from 'moment';
import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { TipoEventoOperacional } from 'app/shared/model/enumerations/tipo-evento-operacional.model';

export interface IEventoOperacional {
  id?: number;
  idUsuarioRegistro?: number;
  tipo?: TipoEventoOperacional;
  titulo?: string;
  descricao?: any;
  dataRegistro?: Moment;
  dataEvento?: Moment;
  duracao?: number;
  houveParadaProducao?: boolean;
  processo?: IProcesso;
  anexos?: IAnexo[];
}

export class EventoOperacional implements IEventoOperacional {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public tipo?: TipoEventoOperacional,
    public titulo?: string,
    public descricao?: any,
    public dataRegistro?: Moment,
    public dataEvento?: Moment,
    public duracao?: number,
    public houveParadaProducao?: boolean,
    public processo?: IProcesso,
    public anexos?: IAnexo[]
  ) {
    this.houveParadaProducao = this.houveParadaProducao || false;
  }
}
