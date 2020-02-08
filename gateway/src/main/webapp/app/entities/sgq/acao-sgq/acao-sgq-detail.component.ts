import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';

@Component({
  selector: 'jhi-acao-sgq-detail',
  templateUrl: './acao-sgq-detail.component.html'
})
export class AcaoSGQDetailComponent implements OnInit {
  acaoSGQ: IAcaoSGQ | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acaoSGQ }) => {
      this.acaoSGQ = acaoSGQ;
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
