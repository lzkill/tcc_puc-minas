import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';
import { SolicitacaoAnaliseService } from './solicitacao-analise.service';

@Component({
  templateUrl: './solicitacao-analise-delete-dialog.component.html'
})
export class SolicitacaoAnaliseDeleteDialogComponent {
  solicitacaoAnalise?: ISolicitacaoAnalise;

  constructor(
    protected solicitacaoAnaliseService: SolicitacaoAnaliseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.solicitacaoAnaliseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('solicitacaoAnaliseListModification');
      this.activeModal.close();
    });
  }
}
