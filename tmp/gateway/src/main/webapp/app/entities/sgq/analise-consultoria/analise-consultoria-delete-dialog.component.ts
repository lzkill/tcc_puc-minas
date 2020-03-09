import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { AnaliseConsultoriaService } from './analise-consultoria.service';

@Component({
  templateUrl: './analise-consultoria-delete-dialog.component.html'
})
export class AnaliseConsultoriaDeleteDialogComponent {
  analiseConsultoria?: IAnaliseConsultoria;

  constructor(
    protected analiseConsultoriaService: AnaliseConsultoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.analiseConsultoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('analiseConsultoriaListModification');
      this.activeModal.close();
    });
  }
}
