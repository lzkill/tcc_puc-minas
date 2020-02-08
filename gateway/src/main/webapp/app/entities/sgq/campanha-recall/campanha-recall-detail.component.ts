import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';

@Component({
  selector: 'jhi-campanha-recall-detail',
  templateUrl: './campanha-recall-detail.component.html'
})
export class CampanhaRecallDetailComponent implements OnInit {
  campanhaRecall: ICampanhaRecall | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campanhaRecall }) => {
      this.campanhaRecall = campanhaRecall;
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
