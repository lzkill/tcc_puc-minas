import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IItemPlanoAuditoria, ItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';
import { ItemPlanoAuditoriaService } from './item-plano-auditoria.service';
import { ItemPlanoAuditoriaComponent } from './item-plano-auditoria.component';
import { ItemPlanoAuditoriaDetailComponent } from './item-plano-auditoria-detail.component';
import { ItemPlanoAuditoriaUpdateComponent } from './item-plano-auditoria-update.component';

@Injectable({ providedIn: 'root' })
export class ItemPlanoAuditoriaResolve implements Resolve<IItemPlanoAuditoria> {
  constructor(private service: ItemPlanoAuditoriaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemPlanoAuditoria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((itemPlanoAuditoria: HttpResponse<ItemPlanoAuditoria>) => {
          if (itemPlanoAuditoria.body) {
            return of(itemPlanoAuditoria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemPlanoAuditoria());
  }
}

export const itemPlanoAuditoriaRoute: Routes = [
  {
    path: '',
    component: ItemPlanoAuditoriaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqItemPlanoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ItemPlanoAuditoriaDetailComponent,
    resolve: {
      itemPlanoAuditoria: ItemPlanoAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqItemPlanoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ItemPlanoAuditoriaUpdateComponent,
    resolve: {
      itemPlanoAuditoria: ItemPlanoAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqItemPlanoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ItemPlanoAuditoriaUpdateComponent,
    resolve: {
      itemPlanoAuditoria: ItemPlanoAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqItemPlanoAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
