import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmpresa } from 'app/shared/model/sgq/empresa.model';
import { EmpresaService } from './empresa.service';

@Component({
  templateUrl: './empresa-delete-dialog.component.html'
})
export class EmpresaDeleteDialogComponent {
  empresa?: IEmpresa;

  constructor(protected empresaService: EmpresaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.empresaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('empresaListModification');
      this.activeModal.close();
    });
  }
}
