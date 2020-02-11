import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ResultadoAuditoriaComponent } from './resultado-auditoria.component';
import { ResultadoAuditoriaDetailComponent } from './resultado-auditoria-detail.component';
import { ResultadoAuditoriaUpdateComponent } from './resultado-auditoria-update.component';
import { ResultadoAuditoriaDeleteDialogComponent } from './resultado-auditoria-delete-dialog.component';
import { resultadoAuditoriaRoute } from './resultado-auditoria.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(resultadoAuditoriaRoute)],
  declarations: [
    ResultadoAuditoriaComponent,
    ResultadoAuditoriaDetailComponent,
    ResultadoAuditoriaUpdateComponent,
    ResultadoAuditoriaDeleteDialogComponent
  ],
  entryComponents: [ResultadoAuditoriaDeleteDialogComponent]
})
export class SgqResultadoAuditoriaModule {}
