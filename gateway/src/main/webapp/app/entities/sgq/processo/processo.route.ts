import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProcesso, Processo } from 'app/shared/model/sgq/processo.model';
import { ProcessoService } from './processo.service';
import { ProcessoComponent } from './processo.component';
import { ProcessoDetailComponent } from './processo-detail.component';
import { ProcessoUpdateComponent } from './processo-update.component';

@Injectable({ providedIn: 'root' })
export class ProcessoResolve implements Resolve<IProcesso> {
  constructor(private service: ProcessoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcesso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((processo: HttpResponse<Processo>) => {
          if (processo.body) {
            return of(processo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Processo());
  }
}

export const processoRoute: Routes = [
  {
    path: '',
    component: ProcessoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqProcesso.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProcessoDetailComponent,
    resolve: {
      processo: ProcessoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqProcesso.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProcessoUpdateComponent,
    resolve: {
      processo: ProcessoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqProcesso.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProcessoUpdateComponent,
    resolve: {
      processo: ProcessoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqProcesso.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
