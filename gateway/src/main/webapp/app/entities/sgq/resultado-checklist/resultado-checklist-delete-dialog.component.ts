import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';
import { ResultadoChecklistService } from './resultado-checklist.service';

@Component({
  templateUrl: './resultado-checklist-delete-dialog.component.html'
})
export class ResultadoChecklistDeleteDialogComponent {
  resultadoChecklist?: IResultadoChecklist;

  constructor(
    protected resultadoChecklistService: ResultadoChecklistService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultadoChecklistService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resultadoChecklistListModification');
      this.activeModal.close();
    });
  }
}
