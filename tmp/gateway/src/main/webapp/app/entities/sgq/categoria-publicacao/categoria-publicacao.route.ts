import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoriaPublicacao, CategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';
import { CategoriaPublicacaoService } from './categoria-publicacao.service';
import { CategoriaPublicacaoComponent } from './categoria-publicacao.component';
import { CategoriaPublicacaoDetailComponent } from './categoria-publicacao-detail.component';
import { CategoriaPublicacaoUpdateComponent } from './categoria-publicacao-update.component';

@Injectable({ providedIn: 'root' })
export class CategoriaPublicacaoResolve implements Resolve<ICategoriaPublicacao> {
  constructor(private service: CategoriaPublicacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaPublicacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categoriaPublicacao: HttpResponse<CategoriaPublicacao>) => {
          if (categoriaPublicacao.body) {
            return of(categoriaPublicacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoriaPublicacao());
  }
}

export const categoriaPublicacaoRoute: Routes = [
  {
    path: '',
    component: CategoriaPublicacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqCategoriaPublicacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaPublicacaoDetailComponent,
    resolve: {
      categoriaPublicacao: CategoriaPublicacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqCategoriaPublicacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriaPublicacaoUpdateComponent,
    resolve: {
      categoriaPublicacao: CategoriaPublicacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqCategoriaPublicacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriaPublicacaoUpdateComponent,
    resolve: {
      categoriaPublicacao: CategoriaPublicacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqCategoriaPublicacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
