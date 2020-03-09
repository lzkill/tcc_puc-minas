import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INorma, Norma } from 'app/shared/model/norma.model';
import { NormaService } from './norma.service';
import { NormaComponent } from './norma.component';
import { NormaDetailComponent } from './norma-detail.component';

@Injectable({ providedIn: 'root' })
export class NormaResolve implements Resolve<INorma> {
  constructor(private service: NormaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INorma> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((norma: HttpResponse<Norma>) => {
          if (norma.body) {
            return of(norma.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Norma());
  }
}

export const normaRoute: Routes = [
  {
    path: '',
    component: NormaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.norma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NormaDetailComponent,
    resolve: {
      norma: NormaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.norma.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
