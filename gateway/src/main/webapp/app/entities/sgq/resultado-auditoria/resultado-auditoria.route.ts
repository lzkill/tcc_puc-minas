import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResultadoAuditoria, ResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';
import { ResultadoAuditoriaService } from './resultado-auditoria.service';
import { ResultadoAuditoriaComponent } from './resultado-auditoria.component';
import { ResultadoAuditoriaDetailComponent } from './resultado-auditoria-detail.component';
import { ResultadoAuditoriaUpdateComponent } from './resultado-auditoria-update.component';

@Injectable({ providedIn: 'root' })
export class ResultadoAuditoriaResolve implements Resolve<IResultadoAuditoria> {
  constructor(private service: ResultadoAuditoriaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultadoAuditoria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resultadoAuditoria: HttpResponse<ResultadoAuditoria>) => {
          if (resultadoAuditoria.body) {
            return of(resultadoAuditoria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultadoAuditoria());
  }
}

export const resultadoAuditoriaRoute: Routes = [
  {
    path: '',
    component: ResultadoAuditoriaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqResultadoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResultadoAuditoriaDetailComponent,
    resolve: {
      resultadoAuditoria: ResultadoAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqResultadoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResultadoAuditoriaUpdateComponent,
    resolve: {
      resultadoAuditoria: ResultadoAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqResultadoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResultadoAuditoriaUpdateComponent,
    resolve: {
      resultadoAuditoria: ResultadoAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqResultadoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
