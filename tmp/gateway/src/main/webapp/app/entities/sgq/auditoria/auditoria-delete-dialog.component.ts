import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';
import { AuditoriaService } from './auditoria.service';

@Component({
  templateUrl: './auditoria-delete-dialog.component.html'
})
export class AuditoriaDeleteDialogComponent {
  auditoria?: IAuditoria;

  constructor(protected auditoriaService: AuditoriaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('auditoriaListModification');
      this.activeModal.close();
    });
  }
}
