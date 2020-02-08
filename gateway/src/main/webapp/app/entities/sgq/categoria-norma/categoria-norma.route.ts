import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoriaNorma, CategoriaNorma } from 'app/shared/model/sgq/categoria-norma.model';
import { CategoriaNormaService } from './categoria-norma.service';
import { CategoriaNormaComponent } from './categoria-norma.component';
import { CategoriaNormaDetailComponent } from './categoria-norma-detail.component';

@Injectable({ providedIn: 'root' })
export class CategoriaNormaResolve implements Resolve<ICategoriaNorma> {
  constructor(private service: CategoriaNormaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaNorma> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categoriaNorma: HttpResponse<CategoriaNorma>) => {
          if (categoriaNorma.body) {
            return of(categoriaNorma.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoriaNorma());
  }
}

export const categoriaNormaRoute: Routes = [
  {
    path: '',
    component: CategoriaNormaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqCategoriaNorma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaNormaDetailComponent,
    resolve: {
      categoriaNorma: CategoriaNormaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqCategoriaNorma.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
