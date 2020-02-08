import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { CategoriaNormaComponent } from './categoria-norma.component';
import { CategoriaNormaDetailComponent } from './categoria-norma-detail.component';
import { categoriaNormaRoute } from './categoria-norma.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(categoriaNormaRoute)],
  declarations: [CategoriaNormaComponent, CategoriaNormaDetailComponent],
  entryComponents: []
})
export class SgqCategoriaNormaModule {}
