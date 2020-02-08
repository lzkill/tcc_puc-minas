import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISetor } from 'app/shared/model/sgq/setor.model';
import { SetorService } from './setor.service';

@Component({
  templateUrl: './setor-delete-dialog.component.html'
})
export class SetorDeleteDialogComponent {
  setor?: ISetor;

  constructor(protected setorService: SetorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.setorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('setorListModification');
      this.activeModal.close();
    });
  }
}
