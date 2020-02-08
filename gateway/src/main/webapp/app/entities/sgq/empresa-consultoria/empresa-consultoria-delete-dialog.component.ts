import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';
import { EmpresaConsultoriaService } from './empresa-consultoria.service';

@Component({
  templateUrl: './empresa-consultoria-delete-dialog.component.html'
})
export class EmpresaConsultoriaDeleteDialogComponent {
  empresaConsultoria?: IEmpresaConsultoria;

  constructor(
    protected empresaConsultoriaService: EmpresaConsultoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.empresaConsultoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('empresaConsultoriaListModification');
      this.activeModal.close();
    });
  }
}
