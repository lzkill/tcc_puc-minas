import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProduto, Produto } from 'app/shared/model/sgq/produto.model';
import { ProdutoService } from './produto.service';
import { ProdutoComponent } from './produto.component';
import { ProdutoDetailComponent } from './produto-detail.component';
import { ProdutoUpdateComponent } from './produto-update.component';

@Injectable({ providedIn: 'root' })
export class ProdutoResolve implements Resolve<IProduto> {
  constructor(private service: ProdutoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProduto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((produto: HttpResponse<Produto>) => {
          if (produto.body) {
            return of(produto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Produto());
  }
}

export const produtoRoute: Routes = [
  {
    path: '',
    component: ProdutoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProdutoDetailComponent,
    resolve: {
      produto: ProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProdutoUpdateComponent,
    resolve: {
      produto: ProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProdutoUpdateComponent,
    resolve: {
      produto: ProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
