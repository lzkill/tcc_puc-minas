import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPublicoAlvo, PublicoAlvo } from 'app/shared/model/sgq/publico-alvo.model';
import { PublicoAlvoService } from './publico-alvo.service';
import { PublicoAlvoComponent } from './publico-alvo.component';
import { PublicoAlvoDetailComponent } from './publico-alvo-detail.component';
import { PublicoAlvoUpdateComponent } from './publico-alvo-update.component';

@Injectable({ providedIn: 'root' })
export class PublicoAlvoResolve implements Resolve<IPublicoAlvo> {
  constructor(private service: PublicoAlvoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPublicoAlvo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((publicoAlvo: HttpResponse<PublicoAlvo>) => {
          if (publicoAlvo.body) {
            return of(publicoAlvo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PublicoAlvo());
  }
}

export const publicoAlvoRoute: Routes = [
  {
    path: '',
    component: PublicoAlvoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqPublicoAlvo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PublicoAlvoDetailComponent,
    resolve: {
      publicoAlvo: PublicoAlvoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqPublicoAlvo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PublicoAlvoUpdateComponent,
    resolve: {
      publicoAlvo: PublicoAlvoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqPublicoAlvo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PublicoAlvoUpdateComponent,
    resolve: {
      publicoAlvo: PublicoAlvoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqPublicoAlvo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
