import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';

@Component({
  selector: 'jhi-produto-nao-conforme-detail',
  templateUrl: './produto-nao-conforme-detail.component.html'
})
export class ProdutoNaoConformeDetailComponent implements OnInit {
  produtoNaoConforme: IProdutoNaoConforme | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produtoNaoConforme }) => {
      this.produtoNaoConforme = produtoNaoConforme;
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
