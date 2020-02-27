import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IItemAuditoria, ItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';
import { ItemAuditoriaService } from './item-auditoria.service';
import { ItemAuditoriaComponent } from './item-auditoria.component';
import { ItemAuditoriaDetailComponent } from './item-auditoria-detail.component';
import { ItemAuditoriaUpdateComponent } from './item-auditoria-update.component';

@Injectable({ providedIn: 'root' })
export class ItemAuditoriaResolve implements Resolve<IItemAuditoria> {
  constructor(private service: ItemAuditoriaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemAuditoria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((itemAuditoria: HttpResponse<ItemAuditoria>) => {
          if (itemAuditoria.body) {
            return of(itemAuditoria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemAuditoria());
  }
}

export const itemAuditoriaRoute: Routes = [
  {
    path: '',
    component: ItemAuditoriaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqItemAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ItemAuditoriaDetailComponent,
    resolve: {
      itemAuditoria: ItemAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqItemAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ItemAuditoriaUpdateComponent,
    resolve: {
      itemAuditoria: ItemAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqItemAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ItemAuditoriaUpdateComponent,
    resolve: {
      itemAuditoria: ItemAuditoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqItemAuditoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
