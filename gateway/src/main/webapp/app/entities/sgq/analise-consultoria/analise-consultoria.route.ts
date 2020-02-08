import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnaliseConsultoria, AnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { AnaliseConsultoriaService } from './analise-consultoria.service';
import { AnaliseConsultoriaComponent } from './analise-consultoria.component';
import { AnaliseConsultoriaDetailComponent } from './analise-consultoria-detail.component';
import { AnaliseConsultoriaUpdateComponent } from './analise-consultoria-update.component';

@Injectable({ providedIn: 'root' })
export class AnaliseConsultoriaResolve implements Resolve<IAnaliseConsultoria> {
  constructor(private service: AnaliseConsultoriaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnaliseConsultoria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((analiseConsultoria: HttpResponse<AnaliseConsultoria>) => {
          if (analiseConsultoria.body) {
            return of(analiseConsultoria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AnaliseConsultoria());
  }
}

export const analiseConsultoriaRoute: Routes = [
  {
    path: '',
    component: AnaliseConsultoriaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqAnaliseConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnaliseConsultoriaDetailComponent,
    resolve: {
      analiseConsultoria: AnaliseConsultoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAnaliseConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnaliseConsultoriaUpdateComponent,
    resolve: {
      analiseConsultoria: AnaliseConsultoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAnaliseConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnaliseConsultoriaUpdateComponent,
    resolve: {
      analiseConsultoria: AnaliseConsultoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAnaliseConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
