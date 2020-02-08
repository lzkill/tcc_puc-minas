import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';

@Component({
  selector: 'jhi-analise-consultoria-detail',
  templateUrl: './analise-consultoria-detail.component.html'
})
export class AnaliseConsultoriaDetailComponent implements OnInit {
  analiseConsultoria: IAnaliseConsultoria | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analiseConsultoria }) => {
      this.analiseConsultoria = analiseConsultoria;
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
