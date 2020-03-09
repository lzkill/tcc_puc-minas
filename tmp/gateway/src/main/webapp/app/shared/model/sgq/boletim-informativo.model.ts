import { Moment } from 'moment';
import { IPublicoAlvo } from 'app/shared/model/sgq/publico-alvo.model';
import { ICategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { StatusPublicacao } from 'app/shared/model/enumerations/status-publicacao.model';

export interface IBoletimInformativo {
  id?: number;
  idUsuarioRegistro?: number;
  titulo?: string;
  descricao?: any;
  dataRegistro?: Moment;
  dataPublicacao?: Moment;
  status?: StatusPublicacao;
  publicoAlvo?: IPublicoAlvo;
  categorias?: ICategoriaPublicacao[];
  anexos?: IAnexo[];
}

export class BoletimInformativo implements IBoletimInformativo {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public titulo?: string,
    public descricao?: any,
    public dataRegistro?: Moment,
    public dataPublicacao?: Moment,
    public status?: StatusPublicacao,
    public publicoAlvo?: IPublicoAlvo,
    public categorias?: ICategoriaPublicacao[],
    public anexos?: IAnexo[]
  ) {}
}
