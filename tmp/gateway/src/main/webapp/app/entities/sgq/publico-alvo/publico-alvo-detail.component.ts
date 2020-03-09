import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPublicoAlvo } from 'app/shared/model/sgq/publico-alvo.model';

@Component({
  selector: 'jhi-publico-alvo-detail',
  templateUrl: './publico-alvo-detail.component.html'
})
export class PublicoAlvoDetailComponent implements OnInit {
  publicoAlvo: IPublicoAlvo | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicoAlvo }) => {
      this.publicoAlvo = publicoAlvo;
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
