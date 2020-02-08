import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ProdutoNaoConformeComponent } from './produto-nao-conforme.component';
import { ProdutoNaoConformeDetailComponent } from './produto-nao-conforme-detail.component';
import { ProdutoNaoConformeUpdateComponent } from './produto-nao-conforme-update.component';
import { ProdutoNaoConformeDeleteDialogComponent } from './produto-nao-conforme-delete-dialog.component';
import { produtoNaoConformeRoute } from './produto-nao-conforme.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(produtoNaoConformeRoute)],
  declarations: [
    ProdutoNaoConformeComponent,
    ProdutoNaoConformeDetailComponent,
    ProdutoNaoConformeUpdateComponent,
    ProdutoNaoConformeDeleteDialogComponent
  ],
  entryComponents: [ProdutoNaoConformeDeleteDialogComponent]
})
export class SgqProdutoNaoConformeModule {}
