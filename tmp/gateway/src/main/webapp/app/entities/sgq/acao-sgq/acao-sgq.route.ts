import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAcaoSGQ, AcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { AcaoSGQService } from './acao-sgq.service';
import { AcaoSGQComponent } from './acao-sgq.component';
import { AcaoSGQDetailComponent } from './acao-sgq-detail.component';
import { AcaoSGQUpdateComponent } from './acao-sgq-update.component';

@Injectable({ providedIn: 'root' })
export class AcaoSGQResolve implements Resolve<IAcaoSGQ> {
  constructor(private service: AcaoSGQService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcaoSGQ> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((acaoSGQ: HttpResponse<AcaoSGQ>) => {
          if (acaoSGQ.body) {
            return of(acaoSGQ.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AcaoSGQ());
  }
}

export const acaoSGQRoute: Routes = [
  {
    path: '',
    component: AcaoSGQComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqAcaoSgq.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AcaoSGQDetailComponent,
    resolve: {
      acaoSGQ: AcaoSGQResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAcaoSgq.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AcaoSGQUpdateComponent,
    resolve: {
      acaoSGQ: AcaoSGQResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAcaoSgq.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AcaoSGQUpdateComponent,
    resolve: {
      acaoSGQ: AcaoSGQResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAcaoSgq.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
