import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeed } from 'app/shared/model/sgq/feed.model';
import { FeedService } from './feed.service';

@Component({
  templateUrl: './feed-delete-dialog.component.html'
})
export class FeedDeleteDialogComponent {
  feed?: IFeed;

  constructor(protected feedService: FeedService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('feedListModification');
      this.activeModal.close();
    });
  }
}
