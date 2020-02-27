import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { SolicitacaoAnaliseComponent } from './solicitacao-analise.component';
import { SolicitacaoAnaliseDetailComponent } from './solicitacao-analise-detail.component';
import { SolicitacaoAnaliseUpdateComponent } from './solicitacao-analise-update.component';
import { SolicitacaoAnaliseDeleteDialogComponent } from './solicitacao-analise-delete-dialog.component';
import { solicitacaoAnaliseRoute } from './solicitacao-analise.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(solicitacaoAnaliseRoute)],
  declarations: [
    SolicitacaoAnaliseComponent,
    SolicitacaoAnaliseDetailComponent,
    SolicitacaoAnaliseUpdateComponent,
    SolicitacaoAnaliseDeleteDialogComponent
  ],
  entryComponents: [SolicitacaoAnaliseDeleteDialogComponent]
})
export class SgqSolicitacaoAnaliseModule {}
