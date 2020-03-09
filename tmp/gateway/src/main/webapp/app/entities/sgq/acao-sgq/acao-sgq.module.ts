import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { AcaoSGQComponent } from './acao-sgq.component';
import { AcaoSGQDetailComponent } from './acao-sgq-detail.component';
import { AcaoSGQUpdateComponent } from './acao-sgq-update.component';
import { AcaoSGQDeleteDialogComponent } from './acao-sgq-delete-dialog.component';
import { acaoSGQRoute } from './acao-sgq.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(acaoSGQRoute)],
  declarations: [AcaoSGQComponent, AcaoSGQDetailComponent, AcaoSGQUpdateComponent, AcaoSGQDeleteDialogComponent],
  entryComponents: [AcaoSGQDeleteDialogComponent]
})
export class SgqAcaoSGQModule {}
