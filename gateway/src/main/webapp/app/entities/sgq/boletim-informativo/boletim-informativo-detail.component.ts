import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';

@Component({
  selector: 'jhi-boletim-informativo-detail',
  templateUrl: './boletim-informativo-detail.component.html'
})
export class BoletimInformativoDetailComponent implements OnInit {
  boletimInformativo: IBoletimInformativo | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletimInformativo }) => {
      this.boletimInformativo = boletimInformativo;
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
