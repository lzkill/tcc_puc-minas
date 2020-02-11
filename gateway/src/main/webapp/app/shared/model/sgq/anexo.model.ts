import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';
import { IBoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';
import { ICampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';
import { IEventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';
import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { INorma } from 'app/shared/model/sgq/norma.model';
import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { IProduto } from 'app/shared/model/sgq/produto.model';
import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { IPublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';
import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';
import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';

export interface IAnexo {
  id?: number;
  nomeArquivo?: string;
  conteudoContentType?: string;
  conteudo?: any;
  acaoSGQ?: IAcaoSGQ;
  analiseConsultoria?: IAnaliseConsultoria;
  checklist?: IChecklist;
  boletimInformativo?: IBoletimInformativo;
  campanhaRecall?: ICampanhaRecall;
  eventoOperacional?: IEventoOperacional;
  itemChecklist?: IItemChecklist;
  itemPlanoAuditoria?: IItemPlanoAuditoria;
  naoConformidade?: INaoConformidade;
  norma?: INorma;
  processo?: IProcesso;
  produto?: IProduto;
  planoAuditoria?: IPlanoAuditoria;
  produtoNaoConforme?: IProdutoNaoConforme;
  publicacaoFeed?: IPublicacaoFeed;
  resultadoChecklist?: IResultadoChecklist;
  resultadoItemChecklist?: IResultadoItemChecklist;
}

export class Anexo implements IAnexo {
  constructor(
    public id?: number,
    public nomeArquivo?: string,
    public conteudoContentType?: string,
    public conteudo?: any,
    public acaoSGQ?: IAcaoSGQ,
    public analiseConsultoria?: IAnaliseConsultoria,
    public checklist?: IChecklist,
    public boletimInformativo?: IBoletimInformativo,
    public campanhaRecall?: ICampanhaRecall,
    public eventoOperacional?: IEventoOperacional,
    public itemChecklist?: IItemChecklist,
    public itemPlanoAuditoria?: IItemPlanoAuditoria,
    public naoConformidade?: INaoConformidade,
    public norma?: INorma,
    public processo?: IProcesso,
    public produto?: IProduto,
    public planoAuditoria?: IPlanoAuditoria,
    public produtoNaoConforme?: IProdutoNaoConforme,
    public publicacaoFeed?: IPublicacaoFeed,
    public resultadoChecklist?: IResultadoChecklist,
    public resultadoItemChecklist?: IResultadoItemChecklist
  ) {}
}
