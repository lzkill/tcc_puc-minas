import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConsultoria } from 'app/shared/model/sgq/consultoria.model';
import { ConsultoriaService } from './consultoria.service';

@Component({
  templateUrl: './consultoria-delete-dialog.component.html'
})
export class ConsultoriaDeleteDialogComponent {
  consultoria?: IConsultoria;

  constructor(
    protected consultoriaService: ConsultoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.consultoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('consultoriaListModification');
      this.activeModal.close();
    });
  }
}
