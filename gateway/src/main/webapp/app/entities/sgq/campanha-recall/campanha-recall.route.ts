import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICampanhaRecall, CampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';
import { CampanhaRecallService } from './campanha-recall.service';
import { CampanhaRecallComponent } from './campanha-recall.component';
import { CampanhaRecallDetailComponent } from './campanha-recall-detail.component';
import { CampanhaRecallUpdateComponent } from './campanha-recall-update.component';

@Injectable({ providedIn: 'root' })
export class CampanhaRecallResolve implements Resolve<ICampanhaRecall> {
  constructor(private service: CampanhaRecallService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICampanhaRecall> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((campanhaRecall: HttpResponse<CampanhaRecall>) => {
          if (campanhaRecall.body) {
            return of(campanhaRecall.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CampanhaRecall());
  }
}

export const campanhaRecallRoute: Routes = [
  {
    path: '',
    component: CampanhaRecallComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqCampanhaRecall.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CampanhaRecallDetailComponent,
    resolve: {
      campanhaRecall: CampanhaRecallResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqCampanhaRecall.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CampanhaRecallUpdateComponent,
    resolve: {
      campanhaRecall: CampanhaRecallResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqCampanhaRecall.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CampanhaRecallUpdateComponent,
    resolve: {
      campanhaRecall: CampanhaRecallResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqCampanhaRecall.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
