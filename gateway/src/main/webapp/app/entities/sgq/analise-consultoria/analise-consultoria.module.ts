import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { AnaliseConsultoriaComponent } from './analise-consultoria.component';
import { AnaliseConsultoriaDetailComponent } from './analise-consultoria-detail.component';
import { AnaliseConsultoriaUpdateComponent } from './analise-consultoria-update.component';
import { AnaliseConsultoriaDeleteDialogComponent } from './analise-consultoria-delete-dialog.component';
import { analiseConsultoriaRoute } from './analise-consultoria.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(analiseConsultoriaRoute)],
  declarations: [
    AnaliseConsultoriaComponent,
    AnaliseConsultoriaDetailComponent,
    AnaliseConsultoriaUpdateComponent,
    AnaliseConsultoriaDeleteDialogComponent
  ],
  entryComponents: [AnaliseConsultoriaDeleteDialogComponent]
})
export class SgqAnaliseConsultoriaModule {}
