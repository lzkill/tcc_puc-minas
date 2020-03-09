import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChecklist } from 'app/shared/model/sgq/checklist.model';
import { ChecklistService } from './checklist.service';

@Component({
  templateUrl: './checklist-delete-dialog.component.html'
})
export class ChecklistDeleteDialogComponent {
  checklist?: IChecklist;

  constructor(protected checklistService: ChecklistService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checklistService.delete(id).subscribe(() => {
      this.eventManager.broadcast('checklistListModification');
      this.activeModal.close();
    });
  }
}
