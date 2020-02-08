import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAuditoria, Auditoria } from 'app/shared/model/sgq/auditoria.model';
import { AuditoriaService } from './auditoria.service';
import { AuditoriaComponent } from './auditoria.component';
import { AuditoriaDetailComponent } from './auditoria-detail.component';
import { AuditoriaUpdateComponent } from './auditoria-update.component';

@Injectable({ providedIn: 'root' })
export class AuditoriaResolve implements Resolve<IAuditoria> {
  constructor(private service: AuditoriaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditoria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((auditoria: HttpResponse<Auditoria>) => {
          if (auditoria.body) {
            return of(auditoria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Auditoria());
  }
}

export const auditoriaRoute: Routes = [
  {
    path: '',
    component: AuditoriaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AuditoriaDetailComponent,
    resolve: {
      auditoria: AuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AuditoriaUpdateComponent,
    resolve: {
      auditoria: AuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AuditoriaUpdateComponent,
    resolve: {
      auditoria: AuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
