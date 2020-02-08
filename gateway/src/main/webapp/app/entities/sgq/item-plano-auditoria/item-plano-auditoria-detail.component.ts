import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

@Component({
  selector: 'jhi-item-plano-auditoria-detail',
  templateUrl: './item-plano-auditoria-detail.component.html'
})
export class ItemPlanoAuditoriaDetailComponent implements OnInit {
  itemPlanoAuditoria: IItemPlanoAuditoria | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemPlanoAuditoria }) => {
      this.itemPlanoAuditoria = itemPlanoAuditoria;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
