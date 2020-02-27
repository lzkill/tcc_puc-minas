import { Moment } from 'moment';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';
import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { IBoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';
import { ICampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';
import { IEventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';
import { IItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';
import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { IProduto } from 'app/shared/model/sgq/produto.model';
import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { IPublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';
import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';
import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';

export interface IAnexo {
  id?: number;
  idUsuarioRegistro?: number;
  dataRegistro?: Moment;
  nomeArquivo?: string;
  conteudoContentType?: string;
  conteudo?: any;
  acaoSGQS?: IAcaoSGQ[];
  auditorias?: IAuditoria[];
  analiseConsultorias?: IAnaliseConsultoria[];
  boletimInformativos?: IBoletimInformativo[];
  campanhaRecalls?: ICampanhaRecall[];
  checklists?: IChecklist[];
  eventoOperacionals?: IEventoOperacional[];
  itemAuditorias?: IItemAuditoria[];
  itemChecklists?: IItemChecklist[];
  itemPlanoAuditorias?: IItemPlanoAuditoria[];
  naoConformidades?: INaoConformidade[];
  processos?: IProcesso[];
  produtos?: IProduto[];
  planoAuditorias?: IPlanoAuditoria[];
  produtoNaoConformes?: IProdutoNaoConforme[];
  publicacaoFeeds?: IPublicacaoFeed[];
  resultadoChecklists?: IResultadoChecklist[];
  resultadoItemChecklists?: IResultadoItemChecklist[];
}

export class Anexo implements IAnexo {
  constructor(
    public id?: number,
    public idUsuarioRegistro?: number,
    public dataRegistro?: Moment,
    public nomeArquivo?: string,
    public conteudoContentType?: string,
    public conteudo?: any,
    public acaoSGQS?: IAcaoSGQ[],
    public auditorias?: IAuditoria[],
    public analiseConsultorias?: IAnaliseConsultoria[],
    public boletimInformativos?: IBoletimInformativo[],
    public campanhaRecalls?: ICampanhaRecall[],
    public checklists?: IChecklist[],
    public eventoOperacionals?: IEventoOperacional[],
    public itemAuditorias?: IItemAuditoria[],
    public itemChecklists?: IItemChecklist[],
    public itemPlanoAuditorias?: IItemPlanoAuditoria[],
    public naoConformidades?: INaoConformidade[],
    public processos?: IProcesso[],
    public produtos?: IProduto[],
    public planoAuditorias?: IPlanoAuditoria[],
    public produtoNaoConformes?: IProdutoNaoConforme[],
    public publicacaoFeeds?: IPublicacaoFeed[],
    public resultadoChecklists?: IResultadoChecklist[],
    public resultadoItemChecklists?: IResultadoItemChecklist[]
  ) {}
}
