import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INaoConformidade, NaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { NaoConformidadeService } from './nao-conformidade.service';
import { NaoConformidadeComponent } from './nao-conformidade.component';
import { NaoConformidadeDetailComponent } from './nao-conformidade-detail.component';
import { NaoConformidadeUpdateComponent } from './nao-conformidade-update.component';

@Injectable({ providedIn: 'root' })
export class NaoConformidadeResolve implements Resolve<INaoConformidade> {
  constructor(private service: NaoConformidadeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INaoConformidade> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((naoConformidade: HttpResponse<NaoConformidade>) => {
          if (naoConformidade.body) {
            return of(naoConformidade.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NaoConformidade());
  }
}

export const naoConformidadeRoute: Routes = [
  {
    path: '',
    component: NaoConformidadeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqNaoConformidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NaoConformidadeDetailComponent,
    resolve: {
      naoConformidade: NaoConformidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqNaoConformidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NaoConformidadeUpdateComponent,
    resolve: {
      naoConformidade: NaoConformidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqNaoConformidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NaoConformidadeUpdateComponent,
    resolve: {
      naoConformidade: NaoConformidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqNaoConformidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
