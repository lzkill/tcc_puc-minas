import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPublicacaoFeed, PublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';
import { PublicacaoFeedService } from './publicacao-feed.service';
import { PublicacaoFeedComponent } from './publicacao-feed.component';
import { PublicacaoFeedDetailComponent } from './publicacao-feed-detail.component';
import { PublicacaoFeedUpdateComponent } from './publicacao-feed-update.component';

@Injectable({ providedIn: 'root' })
export class PublicacaoFeedResolve implements Resolve<IPublicacaoFeed> {
  constructor(private service: PublicacaoFeedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPublicacaoFeed> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((publicacaoFeed: HttpResponse<PublicacaoFeed>) => {
          if (publicacaoFeed.body) {
            return of(publicacaoFeed.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PublicacaoFeed());
  }
}

export const publicacaoFeedRoute: Routes = [
  {
    path: '',
    component: PublicacaoFeedComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqPublicacaoFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PublicacaoFeedDetailComponent,
    resolve: {
      publicacaoFeed: PublicacaoFeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqPublicacaoFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PublicacaoFeedUpdateComponent,
    resolve: {
      publicacaoFeed: PublicacaoFeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqPublicacaoFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PublicacaoFeedUpdateComponent,
    resolve: {
      publicacaoFeed: PublicacaoFeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqPublicacaoFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
