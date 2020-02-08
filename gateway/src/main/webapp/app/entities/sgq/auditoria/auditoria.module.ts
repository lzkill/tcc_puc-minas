import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { AuditoriaComponent } from './auditoria.component';
import { AuditoriaDetailComponent } from './auditoria-detail.component';
import { AuditoriaUpdateComponent } from './auditoria-update.component';
import { AuditoriaDeleteDialogComponent } from './auditoria-delete-dialog.component';
import { auditoriaRoute } from './auditoria.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(auditoriaRoute)],
  declarations: [AuditoriaComponent, AuditoriaDetailComponent, AuditoriaUpdateComponent, AuditoriaDeleteDialogComponent],
  entryComponents: [AuditoriaDeleteDialogComponent]
})
export class SgqAuditoriaModule {}
