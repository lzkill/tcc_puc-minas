import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { BoletimInformativoComponent } from './boletim-informativo.component';
import { BoletimInformativoDetailComponent } from './boletim-informativo-detail.component';
import { BoletimInformativoUpdateComponent } from './boletim-informativo-update.component';
import { BoletimInformativoDeleteDialogComponent } from './boletim-informativo-delete-dialog.component';
import { boletimInformativoRoute } from './boletim-informativo.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(boletimInformativoRoute)],
  declarations: [
    BoletimInformativoComponent,
    BoletimInformativoDetailComponent,
    BoletimInformativoUpdateComponent,
    BoletimInformativoDeleteDialogComponent
  ],
  entryComponents: [BoletimInformativoDeleteDialogComponent]
})
export class SgqBoletimInformativoModule {}
