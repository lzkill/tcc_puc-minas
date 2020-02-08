import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';
import { ResultadoItemChecklistService } from './resultado-item-checklist.service';

@Component({
  templateUrl: './resultado-item-checklist-delete-dialog.component.html'
})
export class ResultadoItemChecklistDeleteDialogComponent {
  resultadoItemChecklist?: IResultadoItemChecklist;

  constructor(
    protected resultadoItemChecklistService: ResultadoItemChecklistService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultadoItemChecklistService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resultadoItemChecklistListModification');
      this.activeModal.close();
    });
  }
}
