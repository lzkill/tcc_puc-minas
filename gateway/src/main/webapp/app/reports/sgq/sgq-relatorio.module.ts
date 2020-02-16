import { NgModule } from '@angular/core';
import { GatewaySharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { reportRoute } from './sgq-relatorio.route';
import { SgqRelatorioComponent } from './sgq-relatorio-component';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(reportRoute)],
  declarations: [SgqRelatorioComponent],
  entryComponents: [SgqRelatorioComponent]
})
export class SgqRelatorioModule {}
