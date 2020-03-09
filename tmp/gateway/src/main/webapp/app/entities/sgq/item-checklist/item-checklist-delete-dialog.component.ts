import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { ItemChecklistService } from './item-checklist.service';

@Component({
  templateUrl: './item-checklist-delete-dialog.component.html'
})
export class ItemChecklistDeleteDialogComponent {
  itemChecklist?: IItemChecklist;

  constructor(
    protected itemChecklistService: ItemChecklistService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemChecklistService.delete(id).subscribe(() => {
      this.eventManager.broadcast('itemChecklistListModification');
      this.activeModal.close();
    });
  }
}
