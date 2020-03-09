import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';

@Component({
  selector: 'jhi-publicacao-feed-detail',
  templateUrl: './publicacao-feed-detail.component.html'
})
export class PublicacaoFeedDetailComponent implements OnInit {
  publicacaoFeed: IPublicacaoFeed | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicacaoFeed }) => {
      this.publicacaoFeed = publicacaoFeed;
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
