import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { PlanoAuditoriaComponent } from './plano-auditoria.component';
import { PlanoAuditoriaDetailComponent } from './plano-auditoria-detail.component';
import { PlanoAuditoriaUpdateComponent } from './plano-auditoria-update.component';
import { PlanoAuditoriaDeleteDialogComponent } from './plano-auditoria-delete-dialog.component';
import { planoAuditoriaRoute } from './plano-auditoria.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(planoAuditoriaRoute)],
  declarations: [
    PlanoAuditoriaComponent,
    PlanoAuditoriaDetailComponent,
    PlanoAuditoriaUpdateComponent,
    PlanoAuditoriaDeleteDialogComponent
  ],
  entryComponents: [PlanoAuditoriaDeleteDialogComponent]
})
export class SgqPlanoAuditoriaModule {}
