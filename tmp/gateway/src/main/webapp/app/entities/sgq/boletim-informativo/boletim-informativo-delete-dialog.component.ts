import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';
import { BoletimInformativoService } from './boletim-informativo.service';

@Component({
  templateUrl: './boletim-informativo-delete-dialog.component.html'
})
export class BoletimInformativoDeleteDialogComponent {
  boletimInformativo?: IBoletimInformativo;

  constructor(
    protected boletimInformativoService: BoletimInformativoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.boletimInformativoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('boletimInformativoListModification');
      this.activeModal.close();
    });
  }
}
