import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { CategoriaPublicacaoComponent } from './categoria-publicacao.component';
import { CategoriaPublicacaoDetailComponent } from './categoria-publicacao-detail.component';
import { CategoriaPublicacaoUpdateComponent } from './categoria-publicacao-update.component';
import { CategoriaPublicacaoDeleteDialogComponent } from './categoria-publicacao-delete-dialog.component';
import { categoriaPublicacaoRoute } from './categoria-publicacao.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(categoriaPublicacaoRoute)],
  declarations: [
    CategoriaPublicacaoComponent,
    CategoriaPublicacaoDetailComponent,
    CategoriaPublicacaoUpdateComponent,
    CategoriaPublicacaoDeleteDialogComponent
  ],
  entryComponents: [CategoriaPublicacaoDeleteDialogComponent]
})
export class SgqCategoriaPublicacaoModule {}
