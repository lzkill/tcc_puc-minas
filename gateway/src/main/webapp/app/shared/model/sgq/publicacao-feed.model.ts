import { Moment } from 'moment';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IFeed } from 'app/shared/model/sgq/feed.model';
import { ICategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';
import { StatusPublicacao } from 'app/shared/model/enumerations/status-publicacao.model';

export interface IPublicacaoFeed {
  id?: number;
  idUsuarioRegistro?: number;
  titulo?: string;
  autor?: string;
  uri?: string;
  link?: string;
  conteudo?: any;
  dataRegistro?: Moment;
  dataPublicacao?: Moment;
  status?: StatusPublicacao;
  anexos?: IAnexo[];
  feed?: IFeed;
  categorias?: ICategoriaPublicacao[];
}

export class PublicacaoFeed implements IPublicacaoFeed {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public titulo?: string,
    public autor?: string,
    public uri?: string,
    public link?: string,
    public conteudo?: any,
    public dataRegistro?: Moment,
    public dataPublicacao?: Moment,
    public status?: StatusPublicacao,
    public anexos?: IAnexo[],
    public feed?: IFeed,
    public categorias?: ICategoriaPublicacao[]
  ) {}
}
