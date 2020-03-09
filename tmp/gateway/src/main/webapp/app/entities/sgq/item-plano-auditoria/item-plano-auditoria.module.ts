import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ItemPlanoAuditoriaComponent } from './item-plano-auditoria.component';
import { ItemPlanoAuditoriaDetailComponent } from './item-plano-auditoria-detail.component';
import { ItemPlanoAuditoriaUpdateComponent } from './item-plano-auditoria-update.component';
import { ItemPlanoAuditoriaDeleteDialogComponent } from './item-plano-auditoria-delete-dialog.component';
import { itemPlanoAuditoriaRoute } from './item-plano-auditoria.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(itemPlanoAuditoriaRoute)],
  declarations: [
    ItemPlanoAuditoriaComponent,
    ItemPlanoAuditoriaDetailComponent,
    ItemPlanoAuditoriaUpdateComponent,
    ItemPlanoAuditoriaDeleteDialogComponent
  ],
  entryComponents: [ItemPlanoAuditoriaDeleteDialogComponent]
})
export class SgqItemPlanoAuditoriaModule {}
