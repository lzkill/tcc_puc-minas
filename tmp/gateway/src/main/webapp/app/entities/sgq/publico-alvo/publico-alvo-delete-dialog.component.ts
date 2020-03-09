import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPublicoAlvo } from 'app/shared/model/sgq/publico-alvo.model';
import { PublicoAlvoService } from './publico-alvo.service';

@Component({
  templateUrl: './publico-alvo-delete-dialog.component.html'
})
export class PublicoAlvoDeleteDialogComponent {
  publicoAlvo?: IPublicoAlvo;

  constructor(
    protected publicoAlvoService: PublicoAlvoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.publicoAlvoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('publicoAlvoListModification');
      this.activeModal.close();
    });
  }
}
