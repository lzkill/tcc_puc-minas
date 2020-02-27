import { IBoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';
import { IPublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';

export interface ICategoriaPublicacao {
  id?: number;
  titulo?: string;
  descricao?: any;
  habilitado?: boolean;
  boletims?: IBoletimInformativo[];
  publicacaos?: IPublicacaoFeed[];
}

export class CategoriaPublicacao implements ICategoriaPublicacao {
  constructor(
    public id?: number,
    public titulo?: string,
    public descricao?: any,
    public habilitado?: boolean,
    public boletims?: IBoletimInformativo[],
    public publicacaos?: IPublicacaoFeed[]
  ) {
    this.habilitado = this.habilitado || false;
  }
}
