import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IChecklist, Checklist } from 'app/shared/model/sgq/checklist.model';
import { ChecklistService } from './checklist.service';
import { ChecklistComponent } from './checklist.component';
import { ChecklistDetailComponent } from './checklist-detail.component';
import { ChecklistUpdateComponent } from './checklist-update.component';

@Injectable({ providedIn: 'root' })
export class ChecklistResolve implements Resolve<IChecklist> {
  constructor(private service: ChecklistService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChecklist> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((checklist: HttpResponse<Checklist>) => {
          if (checklist.body) {
            return of(checklist.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Checklist());
  }
}

export const checklistRoute: Routes = [
  {
    path: '',
    component: ChecklistComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChecklistDetailComponent,
    resolve: {
      checklist: ChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChecklistUpdateComponent,
    resolve: {
      checklist: ChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChecklistUpdateComponent,
    resolve: {
      checklist: ChecklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqChecklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
