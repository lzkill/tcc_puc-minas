import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { ProdutoNaoConformeService } from './produto-nao-conforme.service';

@Component({
  templateUrl: './produto-nao-conforme-delete-dialog.component.html'
})
export class ProdutoNaoConformeDeleteDialogComponent {
  produtoNaoConforme?: IProdutoNaoConforme;

  constructor(
    protected produtoNaoConformeService: ProdutoNaoConformeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.produtoNaoConformeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('produtoNaoConformeListModification');
      this.activeModal.close();
    });
  }
}
