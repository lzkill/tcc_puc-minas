import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProdutoNaoConforme, ProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { ProdutoNaoConformeService } from './produto-nao-conforme.service';
import { ProdutoNaoConformeComponent } from './produto-nao-conforme.component';
import { ProdutoNaoConformeDetailComponent } from './produto-nao-conforme-detail.component';
import { ProdutoNaoConformeUpdateComponent } from './produto-nao-conforme-update.component';

@Injectable({ providedIn: 'root' })
export class ProdutoNaoConformeResolve implements Resolve<IProdutoNaoConforme> {
  constructor(private service: ProdutoNaoConformeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProdutoNaoConforme> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((produtoNaoConforme: HttpResponse<ProdutoNaoConforme>) => {
          if (produtoNaoConforme.body) {
            return of(produtoNaoConforme.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProdutoNaoConforme());
  }
}

export const produtoNaoConformeRoute: Routes = [
  {
    path: '',
    component: ProdutoNaoConformeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqProdutoNaoConforme.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProdutoNaoConformeDetailComponent,
    resolve: {
      produtoNaoConforme: ProdutoNaoConformeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqProdutoNaoConforme.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProdutoNaoConformeUpdateComponent,
    resolve: {
      produtoNaoConforme: ProdutoNaoConformeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqProdutoNaoConforme.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProdutoNaoConformeUpdateComponent,
    resolve: {
      produtoNaoConforme: ProdutoNaoConformeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqProdutoNaoConforme.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
