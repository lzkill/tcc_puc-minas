import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';
import { ItemPlanoAuditoriaService } from './item-plano-auditoria.service';

@Component({
  templateUrl: './item-plano-auditoria-delete-dialog.component.html'
})
export class ItemPlanoAuditoriaDeleteDialogComponent {
  itemPlanoAuditoria?: IItemPlanoAuditoria;

  constructor(
    protected itemPlanoAuditoriaService: ItemPlanoAuditoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemPlanoAuditoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('itemPlanoAuditoriaListModification');
      this.activeModal.close();
    });
  }
}
