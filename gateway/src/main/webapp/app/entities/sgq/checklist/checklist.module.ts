import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ChecklistComponent } from './checklist.component';
import { ChecklistDetailComponent } from './checklist-detail.component';
import { ChecklistUpdateComponent } from './checklist-update.component';
import { ChecklistDeleteDialogComponent } from './checklist-delete-dialog.component';
import { checklistRoute } from './checklist.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(checklistRoute)],
  declarations: [ChecklistComponent, ChecklistDetailComponent, ChecklistUpdateComponent, ChecklistDeleteDialogComponent],
  entryComponents: [ChecklistDeleteDialogComponent]
})
export class SgqChecklistModule {}
