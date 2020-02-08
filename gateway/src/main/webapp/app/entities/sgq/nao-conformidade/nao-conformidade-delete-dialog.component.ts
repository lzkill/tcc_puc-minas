import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { NaoConformidadeService } from './nao-conformidade.service';

@Component({
  templateUrl: './nao-conformidade-delete-dialog.component.html'
})
export class NaoConformidadeDeleteDialogComponent {
  naoConformidade?: INaoConformidade;

  constructor(
    protected naoConformidadeService: NaoConformidadeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.naoConformidadeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('naoConformidadeListModification');
      this.activeModal.close();
    });
  }
}
