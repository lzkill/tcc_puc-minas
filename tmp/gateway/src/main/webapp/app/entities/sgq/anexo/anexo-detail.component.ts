import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnexo } from 'app/shared/model/sgq/anexo.model';

@Component({
  selector: 'jhi-anexo-detail',
  templateUrl: './anexo-detail.component.html'
})
export class AnexoDetailComponent implements OnInit {
  anexo: IAnexo | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexo }) => {
      this.anexo = anexo;
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
