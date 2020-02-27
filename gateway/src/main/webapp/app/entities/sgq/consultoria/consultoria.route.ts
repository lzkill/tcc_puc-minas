import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConsultoria, Consultoria } from 'app/shared/model/sgq/consultoria.model';
import { ConsultoriaService } from './consultoria.service';
import { ConsultoriaComponent } from './consultoria.component';
import { ConsultoriaDetailComponent } from './consultoria-detail.component';
import { ConsultoriaUpdateComponent } from './consultoria-update.component';

@Injectable({ providedIn: 'root' })
export class ConsultoriaResolve implements Resolve<IConsultoria> {
  constructor(private service: ConsultoriaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConsultoria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((consultoria: HttpResponse<Consultoria>) => {
          if (consultoria.body) {
            return of(consultoria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Consultoria());
  }
}

export const consultoriaRoute: Routes = [
  {
    path: '',
    component: ConsultoriaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConsultoriaDetailComponent,
    resolve: {
      consultoria: ConsultoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConsultoriaUpdateComponent,
    resolve: {
      consultoria: ConsultoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConsultoriaUpdateComponent,
    resolve: {
      consultoria: ConsultoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqConsultoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
