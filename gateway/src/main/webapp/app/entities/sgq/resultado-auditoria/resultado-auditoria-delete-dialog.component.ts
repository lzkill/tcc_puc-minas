import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';
import { ResultadoAuditoriaService } from './resultado-auditoria.service';

@Component({
  templateUrl: './resultado-auditoria-delete-dialog.component.html'
})
export class ResultadoAuditoriaDeleteDialogComponent {
  resultadoAuditoria?: IResultadoAuditoria;

  constructor(
    protected resultadoAuditoriaService: ResultadoAuditoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultadoAuditoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resultadoAuditoriaListModification');
      this.activeModal.close();
    });
  }
}
