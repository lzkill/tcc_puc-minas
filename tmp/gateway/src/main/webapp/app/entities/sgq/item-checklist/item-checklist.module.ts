import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ItemChecklistComponent } from './item-checklist.component';
import { ItemChecklistDetailComponent } from './item-checklist-detail.component';
import { ItemChecklistUpdateComponent } from './item-checklist-update.component';
import { ItemChecklistDeleteDialogComponent } from './item-checklist-delete-dialog.component';
import { itemChecklistRoute } from './item-checklist.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(itemChecklistRoute)],
  declarations: [ItemChecklistComponent, ItemChecklistDetailComponent, ItemChecklistUpdateComponent, ItemChecklistDeleteDialogComponent],
  entryComponents: [ItemChecklistDeleteDialogComponent]
})
export class SgqItemChecklistModule {}
