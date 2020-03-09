import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ResultadoChecklistComponent } from './resultado-checklist.component';
import { ResultadoChecklistDetailComponent } from './resultado-checklist-detail.component';
import { ResultadoChecklistUpdateComponent } from './resultado-checklist-update.component';
import { ResultadoChecklistDeleteDialogComponent } from './resultado-checklist-delete-dialog.component';
import { resultadoChecklistRoute } from './resultado-checklist.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(resultadoChecklistRoute)],
  declarations: [
    ResultadoChecklistComponent,
    ResultadoChecklistDetailComponent,
    ResultadoChecklistUpdateComponent,
    ResultadoChecklistDeleteDialogComponent
  ],
  entryComponents: [ResultadoChecklistDeleteDialogComponent]
})
export class SgqResultadoChecklistModule {}
