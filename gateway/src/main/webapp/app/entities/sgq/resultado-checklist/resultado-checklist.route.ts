import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResultadoChecklist, ResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';
import { ResultadoChecklistService } from './resultado-checklist.service';
import { ResultadoChecklistComponent } from './resultado-checklist.component';
import { ResultadoChecklistDetailComponent } from './resultado-checklist-detail.component';
import { ResultadoChecklistUpdateComponent } from './resultado-checklist-update.component';

@Injectable({ providedIn: 'root' })
export class ResultadoChecklistResolve implements Resolve<IResultadoChecklist> {
  constructor(private service: ResultadoChecklistService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultadoChecklist> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resultadoChecklist: HttpResponse<ResultadoChecklist>) => {
          if (resultadoChecklist.body) {
            return of(resultadoChecklist.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultadoChecklist());
  }
}

export const resultadoChecklistRoute: Routes = [
  {
    path: '',
    component: ResultadoChecklistComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqResultadoChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResultadoChecklistDetailComponent,
    resolve: {
      resultadoChecklist: ResultadoChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqResultadoChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResultadoChecklistUpdateComponent,
    resolve: {
      resultadoChecklist: ResultadoChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqResultadoChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResultadoChecklistUpdateComponent,
    resolve: {
      resultadoChecklist: ResultadoChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqResultadoChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
