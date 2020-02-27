import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISolicitacaoAnalise, SolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';
import { SolicitacaoAnaliseService } from './solicitacao-analise.service';
import { SolicitacaoAnaliseComponent } from './solicitacao-analise.component';
import { SolicitacaoAnaliseDetailComponent } from './solicitacao-analise-detail.component';
import { SolicitacaoAnaliseUpdateComponent } from './solicitacao-analise-update.component';

@Injectable({ providedIn: 'root' })
export class SolicitacaoAnaliseResolve implements Resolve<ISolicitacaoAnalise> {
  constructor(private service: SolicitacaoAnaliseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISolicitacaoAnalise> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((solicitacaoAnalise: HttpResponse<SolicitacaoAnalise>) => {
          if (solicitacaoAnalise.body) {
            return of(solicitacaoAnalise.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SolicitacaoAnalise());
  }
}

export const solicitacaoAnaliseRoute: Routes = [
  {
    path: '',
    component: SolicitacaoAnaliseComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqSolicitacaoAnalise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SolicitacaoAnaliseDetailComponent,
    resolve: {
      solicitacaoAnalise: SolicitacaoAnaliseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqSolicitacaoAnalise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SolicitacaoAnaliseUpdateComponent,
    resolve: {
      solicitacaoAnalise: SolicitacaoAnaliseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqSolicitacaoAnalise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SolicitacaoAnaliseUpdateComponent,
    resolve: {
      solicitacaoAnalise: SolicitacaoAnaliseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqSolicitacaoAnalise.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
