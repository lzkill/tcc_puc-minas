import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { NormaComponent } from './norma.component';
import { NormaDetailComponent } from './norma-detail.component';
import { normaRoute } from './norma.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(normaRoute)],
  declarations: [NormaComponent, NormaDetailComponent],
  entryComponents: []
})
export class GatewayNormaModule {}
