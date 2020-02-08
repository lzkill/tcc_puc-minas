import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { EmpresaConsultoriaComponent } from './empresa-consultoria.component';
import { EmpresaConsultoriaDetailComponent } from './empresa-consultoria-detail.component';
import { EmpresaConsultoriaUpdateComponent } from './empresa-consultoria-update.component';
import { EmpresaConsultoriaDeleteDialogComponent } from './empresa-consultoria-delete-dialog.component';
import { empresaConsultoriaRoute } from './empresa-consultoria.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(empresaConsultoriaRoute)],
  declarations: [
    EmpresaConsultoriaComponent,
    EmpresaConsultoriaDetailComponent,
    EmpresaConsultoriaUpdateComponent,
    EmpresaConsultoriaDeleteDialogComponent
  ],
  entryComponents: [EmpresaConsultoriaDeleteDialogComponent]
})
export class SgqEmpresaConsultoriaModule {}
