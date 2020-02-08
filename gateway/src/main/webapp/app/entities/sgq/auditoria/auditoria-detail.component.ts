import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';

@Component({
  selector: 'jhi-auditoria-detail',
  templateUrl: './auditoria-detail.component.html'
})
export class AuditoriaDetailComponent implements OnInit {
  auditoria: IAuditoria | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditoria }) => {
      this.auditoria = auditoria;
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
