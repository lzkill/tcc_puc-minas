import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'empresa',
        loadChildren: () => import('./sgq/empresa/empresa.module').then(m => m.SgqEmpresaModule)
      },
      {
        path: 'setor',
        loadChildren: () => import('./sgq/setor/setor.module').then(m => m.SgqSetorModule)
      },
      {
        path: 'processo',
        loadChildren: () => import('./sgq/processo/processo.module').then(m => m.SgqProcessoModule)
      },
      {
        path: 'produto',
        loadChildren: () => import('./sgq/produto/produto.module').then(m => m.SgqProdutoModule)
      },
      {
        path: 'publico-alvo',
        loadChildren: () => import('./sgq/publico-alvo/publico-alvo.module').then(m => m.SgqPublicoAlvoModule)
      },
      {
        path: 'categoria-publicacao',
        loadChildren: () => import('./sgq/categoria-publicacao/categoria-publicacao.module').then(m => m.SgqCategoriaPublicacaoModule)
      },
      {
        path: 'anexo',
        loadChildren: () => import('./sgq/anexo/anexo.module').then(m => m.SgqAnexoModule)
      },
      {
        path: 'nao-conformidade',
        loadChildren: () => import('./sgq/nao-conformidade/nao-conformidade.module').then(m => m.SgqNaoConformidadeModule)
      },
      {
        path: 'produto-nao-conforme',
        loadChildren: () => import('./sgq/produto-nao-conforme/produto-nao-conforme.module').then(m => m.SgqProdutoNaoConformeModule)
      },
      {
        path: 'acao-sgq',
        loadChildren: () => import('./sgq/acao-sgq/acao-sgq.module').then(m => m.SgqAcaoSGQModule)
      },
      {
        path: 'plano-auditoria',
        loadChildren: () => import('./sgq/plano-auditoria/plano-auditoria.module').then(m => m.SgqPlanoAuditoriaModule)
      },
      {
        path: 'item-plano-auditoria',
        loadChildren: () => import('./sgq/item-plano-auditoria/item-plano-auditoria.module').then(m => m.SgqItemPlanoAuditoriaModule)
      },
      {
        path: 'item-auditoria',
        loadChildren: () => import('./sgq/item-auditoria/item-auditoria.module').then(m => m.SgqItemAuditoriaModule)
      },
      {
        path: 'auditoria',
        loadChildren: () => import('./sgq/auditoria/auditoria.module').then(m => m.SgqAuditoriaModule)
      },
      {
        path: 'checklist',
        loadChildren: () => import('./sgq/checklist/checklist.module').then(m => m.SgqChecklistModule)
      },
      {
        path: 'item-checklist',
        loadChildren: () => import('./sgq/item-checklist/item-checklist.module').then(m => m.SgqItemChecklistModule)
      },
      {
        path: 'resultado-checklist',
        loadChildren: () => import('./sgq/resultado-checklist/resultado-checklist.module').then(m => m.SgqResultadoChecklistModule)
      },
      {
        path: 'resultado-item-checklist',
        loadChildren: () =>
          import('./sgq/resultado-item-checklist/resultado-item-checklist.module').then(m => m.SgqResultadoItemChecklistModule)
      },
      {
        path: 'evento-operacional',
        loadChildren: () => import('./sgq/evento-operacional/evento-operacional.module').then(m => m.SgqEventoOperacionalModule)
      },
      {
        path: 'campanha-recall',
        loadChildren: () => import('./sgq/campanha-recall/campanha-recall.module').then(m => m.SgqCampanhaRecallModule)
      },
      {
        path: 'boletim-informativo',
        loadChildren: () => import('./sgq/boletim-informativo/boletim-informativo.module').then(m => m.SgqBoletimInformativoModule)
      },
      {
        path: 'feed',
        loadChildren: () => import('./sgq/feed/feed.module').then(m => m.SgqFeedModule)
      },
      {
        path: 'publicacao-feed',
        loadChildren: () => import('./sgq/publicacao-feed/publicacao-feed.module').then(m => m.SgqPublicacaoFeedModule)
      },
      {
        path: 'consultoria',
        loadChildren: () => import('./sgq/consultoria/consultoria.module').then(m => m.SgqConsultoriaModule)
      },
      {
        path: 'solicitacao-analise',
        loadChildren: () => import('./sgq/solicitacao-analise/solicitacao-analise.module').then(m => m.SgqSolicitacaoAnaliseModule)
      },
      {
        path: 'analise-consultoria',
        loadChildren: () => import('./sgq/analise-consultoria/analise-consultoria.module').then(m => m.SgqAnaliseConsultoriaModule)
      },
      {
        path: 'norma',
        loadChildren: () => import('./norma/norma.module').then(m => m.GatewayNormaModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class GatewayEntityModule {}
