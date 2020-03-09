import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { PlanoAuditoriaService } from './plano-auditoria.service';

@Component({
  templateUrl: './plano-auditoria-delete-dialog.component.html'
})
export class PlanoAuditoriaDeleteDialogComponent {
  planoAuditoria?: IPlanoAuditoria;

  constructor(
    protected planoAuditoriaService: PlanoAuditoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoAuditoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoAuditoriaListModification');
      this.activeModal.close();
    });
  }
}
