import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';

@Component({
  selector: 'jhi-resultado-auditoria-detail',
  templateUrl: './resultado-auditoria-detail.component.html'
})
export class ResultadoAuditoriaDetailComponent implements OnInit {
  resultadoAuditoria: IResultadoAuditoria | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoAuditoria }) => {
      this.resultadoAuditoria = resultadoAuditoria;
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
