import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';
import { CampanhaRecallService } from './campanha-recall.service';

@Component({
  templateUrl: './campanha-recall-delete-dialog.component.html'
})
export class CampanhaRecallDeleteDialogComponent {
  campanhaRecall?: ICampanhaRecall;

  constructor(
    protected campanhaRecallService: CampanhaRecallService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.campanhaRecallService.delete(id).subscribe(() => {
      this.eventManager.broadcast('campanhaRecallListModification');
      this.activeModal.close();
    });
  }
}
