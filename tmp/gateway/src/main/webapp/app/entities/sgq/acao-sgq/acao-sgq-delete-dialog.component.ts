import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { AcaoSGQService } from './acao-sgq.service';

@Component({
  templateUrl: './acao-sgq-delete-dialog.component.html'
})
export class AcaoSGQDeleteDialogComponent {
  acaoSGQ?: IAcaoSGQ;

  constructor(protected acaoSGQService: AcaoSGQService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.acaoSGQService.delete(id).subscribe(() => {
      this.eventManager.broadcast('acaoSGQListModification');
      this.activeModal.close();
    });
  }
}
