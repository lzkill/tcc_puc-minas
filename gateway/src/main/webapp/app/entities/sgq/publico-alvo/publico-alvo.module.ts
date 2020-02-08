import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { PublicoAlvoComponent } from './publico-alvo.component';
import { PublicoAlvoDetailComponent } from './publico-alvo-detail.component';
import { PublicoAlvoUpdateComponent } from './publico-alvo-update.component';
import { PublicoAlvoDeleteDialogComponent } from './publico-alvo-delete-dialog.component';
import { publicoAlvoRoute } from './publico-alvo.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(publicoAlvoRoute)],
  declarations: [PublicoAlvoComponent, PublicoAlvoDetailComponent, PublicoAlvoUpdateComponent, PublicoAlvoDeleteDialogComponent],
  entryComponents: [PublicoAlvoDeleteDialogComponent]
})
export class SgqPublicoAlvoModule {}
