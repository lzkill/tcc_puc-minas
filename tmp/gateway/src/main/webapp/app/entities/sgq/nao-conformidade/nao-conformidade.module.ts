import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { NaoConformidadeComponent } from './nao-conformidade.component';
import { NaoConformidadeDetailComponent } from './nao-conformidade-detail.component';
import { NaoConformidadeUpdateComponent } from './nao-conformidade-update.component';
import { NaoConformidadeDeleteDialogComponent } from './nao-conformidade-delete-dialog.component';
import { naoConformidadeRoute } from './nao-conformidade.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(naoConformidadeRoute)],
  declarations: [
    NaoConformidadeComponent,
    NaoConformidadeDetailComponent,
    NaoConformidadeUpdateComponent,
    NaoConformidadeDeleteDialogComponent
  ],
  entryComponents: [NaoConformidadeDeleteDialogComponent]
})
export class SgqNaoConformidadeModule {}
