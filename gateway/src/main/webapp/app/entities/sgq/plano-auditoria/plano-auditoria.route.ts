import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoAuditoria, PlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { PlanoAuditoriaService } from './plano-auditoria.service';
import { PlanoAuditoriaComponent } from './plano-auditoria.component';
import { PlanoAuditoriaDetailComponent } from './plano-auditoria-detail.component';
import { PlanoAuditoriaUpdateComponent } from './plano-auditoria-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoAuditoriaResolve implements Resolve<IPlanoAuditoria> {
  constructor(private service: PlanoAuditoriaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoAuditoria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoAuditoria: HttpResponse<PlanoAuditoria>) => {
          if (planoAuditoria.body) {
            return of(planoAuditoria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoAuditoria());
  }
}

export const planoAuditoriaRoute: Routes = [
  {
    path: '',
    component: PlanoAuditoriaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqPlanoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlanoAuditoriaDetailComponent,
    resolve: {
      planoAuditoria: PlanoAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqPlanoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlanoAuditoriaUpdateComponent,
    resolve: {
      planoAuditoria: PlanoAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqPlanoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlanoAuditoriaUpdateComponent,
    resolve: {
      planoAuditoria: PlanoAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqPlanoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
