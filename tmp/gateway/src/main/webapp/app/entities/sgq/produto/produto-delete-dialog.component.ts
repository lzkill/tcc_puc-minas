import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProduto } from 'app/shared/model/sgq/produto.model';
import { ProdutoService } from './produto.service';

@Component({
  templateUrl: './produto-delete-dialog.component.html'
})
export class ProdutoDeleteDialogComponent {
  produto?: IProduto;

  constructor(protected produtoService: ProdutoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.produtoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('produtoListModification');
      this.activeModal.close();
    });
  }
}
