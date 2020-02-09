import { Moment } from 'moment';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { IProduto } from 'app/shared/model/sgq/produto.model';
import { StatusSGQ } from 'app/shared/model/enumerations/status-sgq.model';

export interface IProdutoNaoConforme {
  id?: number;
  idUsuarioRegistro?: number;
  idUsuarioResponsavel?: number;
  titulo?: string;
  descricao?: any;
  procedente?: boolean;
  dataRegistro?: Moment;
  analiseFinal?: any;
  statusSGQ?: StatusSGQ;
  acao?: IAcaoSGQ;
  naoConformidade?: INaoConformidade;
  anexo?: IAnexo;
  produto?: IProduto;
}

export class ProdutoNaoConforme implements IProdutoNaoConforme {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public idUsuarioResponsavel?: number,
    public titulo?: string,
    public descricao?: any,
    public procedente?: boolean,
    public dataRegistro?: Moment,
    public analiseFinal?: any,
    public statusSGQ?: StatusSGQ,
    public acao?: IAcaoSGQ,
    public naoConformidade?: INaoConformidade,
    public anexo?: IAnexo,
    public produto?: IProduto
  ) {
    this.procedente = this.procedente || false;
  }
}
