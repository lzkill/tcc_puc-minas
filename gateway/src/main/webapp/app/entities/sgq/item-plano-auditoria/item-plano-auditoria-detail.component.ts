import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

@Component({
  selector: 'jhi-item-plano-auditoria-detail',
  templateUrl: './item-plano-auditoria-detail.component.html'
})
export class ItemPlanoAuditoriaDetailComponent implements OnInit {
  itemPlanoAuditoria: IItemPlanoAuditoria | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemPlanoAuditoria }) => {
      this.itemPlanoAuditoria = itemPlanoAuditoria;
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
