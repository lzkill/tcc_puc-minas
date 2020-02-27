import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';

@Component({
  selector: 'jhi-item-auditoria-detail',
  templateUrl: './item-auditoria-detail.component.html'
})
export class ItemAuditoriaDetailComponent implements OnInit {
  itemAuditoria: IItemAuditoria | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemAuditoria }) => {
      this.itemAuditoria = itemAuditoria;
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
