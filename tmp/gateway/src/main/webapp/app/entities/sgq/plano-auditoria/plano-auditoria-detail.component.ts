import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';

@Component({
  selector: 'jhi-plano-auditoria-detail',
  templateUrl: './plano-auditoria-detail.component.html'
})
export class PlanoAuditoriaDetailComponent implements OnInit {
  planoAuditoria: IPlanoAuditoria | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoAuditoria }) => {
      this.planoAuditoria = planoAuditoria;
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
