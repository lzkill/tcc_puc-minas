import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { CampanhaRecallComponent } from './campanha-recall.component';
import { CampanhaRecallDetailComponent } from './campanha-recall-detail.component';
import { CampanhaRecallUpdateComponent } from './campanha-recall-update.component';
import { CampanhaRecallDeleteDialogComponent } from './campanha-recall-delete-dialog.component';
import { campanhaRecallRoute } from './campanha-recall.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(campanhaRecallRoute)],
  declarations: [
    CampanhaRecallComponent,
    CampanhaRecallDetailComponent,
    CampanhaRecallUpdateComponent,
    CampanhaRecallDeleteDialogComponent
  ],
  entryComponents: [CampanhaRecallDeleteDialogComponent]
})
export class SgqCampanhaRecallModule {}
