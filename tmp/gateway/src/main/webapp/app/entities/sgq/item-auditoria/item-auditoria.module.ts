import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ItemAuditoriaComponent } from './item-auditoria.component';
import { ItemAuditoriaDetailComponent } from './item-auditoria-detail.component';
import { ItemAuditoriaUpdateComponent } from './item-auditoria-update.component';
import { ItemAuditoriaDeleteDialogComponent } from './item-auditoria-delete-dialog.component';
import { itemAuditoriaRoute } from './item-auditoria.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(itemAuditoriaRoute)],
  declarations: [ItemAuditoriaComponent, ItemAuditoriaDetailComponent, ItemAuditoriaUpdateComponent, ItemAuditoriaDeleteDialogComponent],
  entryComponents: [ItemAuditoriaDeleteDialogComponent]
})
export class SgqItemAuditoriaModule {}
