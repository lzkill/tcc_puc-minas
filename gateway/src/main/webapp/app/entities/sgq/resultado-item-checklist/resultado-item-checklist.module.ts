import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ResultadoItemChecklistComponent } from './resultado-item-checklist.component';
import { ResultadoItemChecklistDetailComponent } from './resultado-item-checklist-detail.component';
import { ResultadoItemChecklistUpdateComponent } from './resultado-item-checklist-update.component';
import { ResultadoItemChecklistDeleteDialogComponent } from './resultado-item-checklist-delete-dialog.component';
import { resultadoItemChecklistRoute } from './resultado-item-checklist.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(resultadoItemChecklistRoute)],
  declarations: [
    ResultadoItemChecklistComponent,
    ResultadoItemChecklistDetailComponent,
    ResultadoItemChecklistUpdateComponent,
    ResultadoItemChecklistDeleteDialogComponent
  ],
  entryComponents: [ResultadoItemChecklistDeleteDialogComponent]
})
export class SgqResultadoItemChecklistModule {}
