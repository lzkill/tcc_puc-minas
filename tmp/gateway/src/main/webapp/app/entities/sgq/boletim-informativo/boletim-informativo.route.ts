import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBoletimInformativo, BoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';
import { BoletimInformativoService } from './boletim-informativo.service';
import { BoletimInformativoComponent } from './boletim-informativo.component';
import { BoletimInformativoDetailComponent } from './boletim-informativo-detail.component';
import { BoletimInformativoUpdateComponent } from './boletim-informativo-update.component';

@Injectable({ providedIn: 'root' })
export class BoletimInformativoResolve implements Resolve<IBoletimInformativo> {
  constructor(private service: BoletimInformativoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBoletimInformativo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((boletimInformativo: HttpResponse<BoletimInformativo>) => {
          if (boletimInformativo.body) {
            return of(boletimInformativo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BoletimInformativo());
  }
}

export const boletimInformativoRoute: Routes = [
  {
    path: '',
    component: BoletimInformativoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqBoletimInformativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BoletimInformativoDetailComponent,
    resolve: {
      boletimInformativo: BoletimInformativoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqBoletimInformativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BoletimInformativoUpdateComponent,
    resolve: {
      boletimInformativo: BoletimInformativoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqBoletimInformativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BoletimInformativoUpdateComponent,
    resolve: {
      boletimInformativo: BoletimInformativoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqBoletimInformativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
