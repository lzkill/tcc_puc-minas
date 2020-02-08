import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';
import { CategoriaPublicacaoService } from './categoria-publicacao.service';

@Component({
  templateUrl: './categoria-publicacao-delete-dialog.component.html'
})
export class CategoriaPublicacaoDeleteDialogComponent {
  categoriaPublicacao?: ICategoriaPublicacao;

  constructor(
    protected categoriaPublicacaoService: CategoriaPublicacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaPublicacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoriaPublicacaoListModification');
      this.activeModal.close();
    });
  }
}
