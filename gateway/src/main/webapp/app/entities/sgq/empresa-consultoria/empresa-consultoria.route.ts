import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmpresaConsultoria, EmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';
import { EmpresaConsultoriaService } from './empresa-consultoria.service';
import { EmpresaConsultoriaComponent } from './empresa-consultoria.component';
import { EmpresaConsultoriaDetailComponent } from './empresa-consultoria-detail.component';
import { EmpresaConsultoriaUpdateComponent } from './empresa-consultoria-update.component';

@Injectable({ providedIn: 'root' })
export class EmpresaConsultoriaResolve implements Resolve<IEmpresaConsultoria> {
  constructor(private service: EmpresaConsultoriaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmpresaConsultoria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((empresaConsultoria: HttpResponse<EmpresaConsultoria>) => {
          if (empresaConsultoria.body) {
            return of(empresaConsultoria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmpresaConsultoria());
  }
}

export const empresaConsultoriaRoute: Routes = [
  {
    path: '',
    component: EmpresaConsultoriaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqEmpresaConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmpresaConsultoriaDetailComponent,
    resolve: {
      empresaConsultoria: EmpresaConsultoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqEmpresaConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmpresaConsultoriaUpdateComponent,
    resolve: {
      empresaConsultoria: EmpresaConsultoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqEmpresaConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmpresaConsultoriaUpdateComponent,
    resolve: {
      empresaConsultoria: EmpresaConsultoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqEmpresaConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
