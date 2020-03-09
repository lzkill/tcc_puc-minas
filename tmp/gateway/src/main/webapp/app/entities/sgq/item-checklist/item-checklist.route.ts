import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IItemChecklist, ItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { ItemChecklistService } from './item-checklist.service';
import { ItemChecklistComponent } from './item-checklist.component';
import { ItemChecklistDetailComponent } from './item-checklist-detail.component';
import { ItemChecklistUpdateComponent } from './item-checklist-update.component';

@Injectable({ providedIn: 'root' })
export class ItemChecklistResolve implements Resolve<IItemChecklist> {
  constructor(private service: ItemChecklistService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemChecklist> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((itemChecklist: HttpResponse<ItemChecklist>) => {
          if (itemChecklist.body) {
            return of(itemChecklist.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemChecklist());
  }
}

export const itemChecklistRoute: Routes = [
  {
    path: '',
    component: ItemChecklistComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqItemChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ItemChecklistDetailComponent,
    resolve: {
      itemChecklist: ItemChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqItemChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ItemChecklistUpdateComponent,
    resolve: {
      itemChecklist: ItemChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqItemChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ItemChecklistUpdateComponent,
    resolve: {
      itemChecklist: ItemChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqItemChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
