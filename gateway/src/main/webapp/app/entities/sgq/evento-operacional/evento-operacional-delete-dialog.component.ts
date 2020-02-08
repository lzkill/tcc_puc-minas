import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';
import { EventoOperacionalService } from './evento-operacional.service';

@Component({
  templateUrl: './evento-operacional-delete-dialog.component.html'
})
export class EventoOperacionalDeleteDialogComponent {
  eventoOperacional?: IEventoOperacional;

  constructor(
    protected eventoOperacionalService: EventoOperacionalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventoOperacionalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eventoOperacionalListModification');
      this.activeModal.close();
    });
  }
}
