import { Moment } from 'moment';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';

export interface IResultadoAuditoria {
  id?: number;
  idUsuarioResponsavel?: number;
  dataInicio?: Moment;
  dataFim?: Moment;
  descricao?: any;
  naoConformidades?: INaoConformidade[];
  produtoNaoConformes?: IProdutoNaoConforme[];
  auditoria?: IAuditoria;
}

export class ResultadoAuditoria implements IResultadoAuditoria {
  constructor(
    public id?: number,
    public idUsuarioResponsavel?: number,
    public dataInicio?: Moment,
    public dataFim?: Moment,
    public descricao?: any,
    public naoConformidades?: INaoConformidade[],
    public produtoNaoConformes?: IProdutoNaoConforme[],
    public auditoria?: IAuditoria
  ) {}
}
