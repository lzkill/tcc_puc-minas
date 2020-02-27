import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';
import { ItemAuditoriaService } from './item-auditoria.service';

@Component({
  templateUrl: './item-auditoria-delete-dialog.component.html'
})
export class ItemAuditoriaDeleteDialogComponent {
  itemAuditoria?: IItemAuditoria;

  constructor(
    protected itemAuditoriaService: ItemAuditoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemAuditoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('itemAuditoriaListModification');
      this.activeModal.close();
    });
  }
}
