import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';

@Component({
  selector: 'jhi-resultado-item-checklist-detail',
  templateUrl: './resultado-item-checklist-detail.component.html'
})
export class ResultadoItemChecklistDetailComponent implements OnInit {
  resultadoItemChecklist: IResultadoItemChecklist | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoItemChecklist }) => {
      this.resultadoItemChecklist = resultadoItemChecklist;
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
