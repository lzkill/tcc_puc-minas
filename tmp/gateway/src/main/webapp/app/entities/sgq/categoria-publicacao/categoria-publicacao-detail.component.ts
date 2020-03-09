import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';

@Component({
  selector: 'jhi-categoria-publicacao-detail',
  templateUrl: './categoria-publicacao-detail.component.html'
})
export class CategoriaPublicacaoDetailComponent implements OnInit {
  categoriaPublicacao: ICategoriaPublicacao | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaPublicacao }) => {
      this.categoriaPublicacao = categoriaPublicacao;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
