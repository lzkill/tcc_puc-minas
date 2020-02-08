import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';
import { PublicacaoFeedService } from './publicacao-feed.service';

@Component({
  templateUrl: './publicacao-feed-delete-dialog.component.html'
})
export class PublicacaoFeedDeleteDialogComponent {
  publicacaoFeed?: IPublicacaoFeed;

  constructor(
    protected publicacaoFeedService: PublicacaoFeedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.publicacaoFeedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('publicacaoFeedListModification');
      this.activeModal.close();
    });
  }
}
