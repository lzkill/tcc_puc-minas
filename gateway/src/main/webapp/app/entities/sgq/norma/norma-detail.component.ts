import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { INorma } from 'app/shared/model/sgq/norma.model';

@Component({
  selector: 'jhi-norma-detail',
  templateUrl: './norma-detail.component.html'
})
export class NormaDetailComponent implements OnInit {
  norma: INorma | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ norma }) => {
      this.norma = norma;
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

  protected download(url: string): void {
    window.open(url, '_blank', '_noopener');
  }
}
