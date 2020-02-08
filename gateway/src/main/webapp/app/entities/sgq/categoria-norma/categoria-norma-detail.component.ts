import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICategoriaNorma } from 'app/shared/model/sgq/categoria-norma.model';

@Component({
  selector: 'jhi-categoria-norma-detail',
  templateUrl: './categoria-norma-detail.component.html'
})
export class CategoriaNormaDetailComponent implements OnInit {
  categoriaNorma: ICategoriaNorma | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaNorma }) => {
      this.categoriaNorma = categoriaNorma;
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
