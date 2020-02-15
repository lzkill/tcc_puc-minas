import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeed } from 'app/shared/model/sgq/feed.model';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-feed-detail',
  templateUrl: './feed-detail.component.html'
})
export class FeedDetailComponent implements OnInit {
  feed: IFeed | null = null;

  usuarios: Map<any, IUser> = new Map<any, IUser>();

  constructor(protected activatedRoute: ActivatedRoute, protected userService: UserService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feed }) => {
      this.feed = feed;

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) =>
          resBody.forEach(item => {
            if (!item.isAdmin()) this.usuarios.set(item.id, item);
          })
        );
    });
  }

  previousState(): void {
    window.history.back();
  }
}
