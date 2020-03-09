import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { PublicacaoFeedComponent } from './publicacao-feed.component';
import { PublicacaoFeedDetailComponent } from './publicacao-feed-detail.component';
import { PublicacaoFeedUpdateComponent } from './publicacao-feed-update.component';
import { PublicacaoFeedDeleteDialogComponent } from './publicacao-feed-delete-dialog.component';
import { publicacaoFeedRoute } from './publicacao-feed.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(publicacaoFeedRoute)],
  declarations: [
    PublicacaoFeedComponent,
    PublicacaoFeedDetailComponent,
    PublicacaoFeedUpdateComponent,
    PublicacaoFeedDeleteDialogComponent
  ],
  entryComponents: [PublicacaoFeedDeleteDialogComponent]
})
export class SgqPublicacaoFeedModule {}
