import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { FeedComponent } from './feed.component';
import { FeedDetailComponent } from './feed-detail.component';
import { FeedUpdateComponent } from './feed-update.component';
import { FeedDeleteDialogComponent } from './feed-delete-dialog.component';
import { feedRoute } from './feed.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(feedRoute)],
  declarations: [FeedComponent, FeedDetailComponent, FeedUpdateComponent, FeedDeleteDialogComponent],
  entryComponents: [FeedDeleteDialogComponent]
})
export class SgqFeedModule {}
