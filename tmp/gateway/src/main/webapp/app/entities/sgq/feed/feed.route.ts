import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFeed, Feed } from 'app/shared/model/sgq/feed.model';
import { FeedService } from './feed.service';
import { FeedComponent } from './feed.component';
import { FeedDetailComponent } from './feed-detail.component';
import { FeedUpdateComponent } from './feed-update.component';

@Injectable({ providedIn: 'root' })
export class FeedResolve implements Resolve<IFeed> {
  constructor(private service: FeedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFeed> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((feed: HttpResponse<Feed>) => {
          if (feed.body) {
            return of(feed.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Feed());
  }
}

export const feedRoute: Routes = [
  {
    path: '',
    component: FeedComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FeedDetailComponent,
    resolve: {
      feed: FeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FeedUpdateComponent,
    resolve: {
      feed: FeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FeedUpdateComponent,
    resolve: {
      feed: FeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
