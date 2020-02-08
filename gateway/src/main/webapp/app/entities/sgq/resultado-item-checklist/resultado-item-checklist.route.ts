import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResultadoItemChecklist, ResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';
import { ResultadoItemChecklistService } from './resultado-item-checklist.service';
import { ResultadoItemChecklistComponent } from './resultado-item-checklist.component';
import { ResultadoItemChecklistDetailComponent } from './resultado-item-checklist-detail.component';
import { ResultadoItemChecklistUpdateComponent } from './resultado-item-checklist-update.component';

@Injectable({ providedIn: 'root' })
export class ResultadoItemChecklistResolve implements Resolve<IResultadoItemChecklist> {
  constructor(private service: ResultadoItemChecklistService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultadoItemChecklist> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resultadoItemChecklist: HttpResponse<ResultadoItemChecklist>) => {
          if (resultadoItemChecklist.body) {
            return of(resultadoItemChecklist.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultadoItemChecklist());
  }
}

export const resultadoItemChecklistRoute: Routes = [
  {
    path: '',
    component: ResultadoItemChecklistComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqResultadoItemChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResultadoItemChecklistDetailComponent,
    resolve: {
      resultadoItemChecklist: ResultadoItemChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqResultadoItemChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResultadoItemChecklistUpdateComponent,
    resolve: {
      resultadoItemChecklist: ResultadoItemChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqResultadoItemChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResultadoItemChecklistUpdateComponent,
    resolve: {
      resultadoItemChecklist: ResultadoItemChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqResultadoItemChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
