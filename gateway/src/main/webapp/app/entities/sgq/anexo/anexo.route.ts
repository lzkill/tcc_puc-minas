import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnexo, Anexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from './anexo.service';
import { AnexoComponent } from './anexo.component';
import { AnexoDetailComponent } from './anexo-detail.component';
import { AnexoUpdateComponent } from './anexo-update.component';

@Injectable({ providedIn: 'root' })
export class AnexoResolve implements Resolve<IAnexo> {
  constructor(private service: AnexoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnexo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((anexo: HttpResponse<Anexo>) => {
          if (anexo.body) {
            return of(anexo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Anexo());
  }
}

export const anexoRoute: Routes = [
  {
    path: '',
    component: AnexoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqAnexo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnexoDetailComponent,
    resolve: {
      anexo: AnexoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAnexo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnexoUpdateComponent,
    resolve: {
      anexo: AnexoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAnexo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnexoUpdateComponent,
    resolve: {
      anexo: AnexoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqAnexo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
