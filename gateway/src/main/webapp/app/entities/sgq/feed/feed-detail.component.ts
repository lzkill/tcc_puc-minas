import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeed } from 'app/shared/model/sgq/feed.model';

@Component({
  selector: 'jhi-feed-detail',
  templateUrl: './feed-detail.component.html'
})
export class FeedDetailComponent implements OnInit {
  feed: IFeed | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feed }) => {
      this.feed = feed;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
