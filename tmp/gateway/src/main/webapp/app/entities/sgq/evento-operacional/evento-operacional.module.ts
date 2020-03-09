import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { EventoOperacionalComponent } from './evento-operacional.component';
import { EventoOperacionalDetailComponent } from './evento-operacional-detail.component';
import { EventoOperacionalUpdateComponent } from './evento-operacional-update.component';
import { EventoOperacionalDeleteDialogComponent } from './evento-operacional-delete-dialog.component';
import { eventoOperacionalRoute } from './evento-operacional.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(eventoOperacionalRoute)],
  declarations: [
    EventoOperacionalComponent,
    EventoOperacionalDetailComponent,
    EventoOperacionalUpdateComponent,
    EventoOperacionalDeleteDialogComponent
  ],
  entryComponents: [EventoOperacionalDeleteDialogComponent]
})
export class SgqEventoOperacionalModule {}
