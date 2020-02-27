import { Moment } from 'moment';
import { TipoFeed } from 'app/shared/model/enumerations/tipo-feed.model';

export interface IFeed {
  id?: number;
  idUsuarioRegistro?: number;
  tipo?: TipoFeed;
  titulo?: string;
  descricao?: string;
  uri?: string;
  link?: string;
  urlImagem?: string;
  tituloImagem?: string;
  alturaImagem?: number;
  larguraImagem?: number;
  dataRegistro?: Moment;
  habilitado?: boolean;
}

export class Feed implements IFeed {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public tipo?: TipoFeed,
    public titulo?: string,
    public descricao?: string,
    public uri?: string,
    public link?: string,
    public urlImagem?: string,
    public tituloImagem?: string,
    public alturaImagem?: number,
    public larguraImagem?: number,
    public dataRegistro?: Moment,
    public habilitado?: boolean
  ) {
    this.habilitado = this.habilitado || false;
  }
}
