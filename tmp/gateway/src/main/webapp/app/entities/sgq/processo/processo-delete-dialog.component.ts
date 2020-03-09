import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { ProcessoService } from './processo.service';

@Component({
  templateUrl: './processo-delete-dialog.component.html'
})
export class ProcessoDeleteDialogComponent {
  processo?: IProcesso;

  constructor(protected processoService: ProcessoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.processoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('processoListModification');
      this.activeModal.close();
    });
  }
}
