import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEventoOperacional, EventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';
import { EventoOperacionalService } from './evento-operacional.service';
import { EventoOperacionalComponent } from './evento-operacional.component';
import { EventoOperacionalDetailComponent } from './evento-operacional-detail.component';
import { EventoOperacionalUpdateComponent } from './evento-operacional-update.component';

@Injectable({ providedIn: 'root' })
export class EventoOperacionalResolve implements Resolve<IEventoOperacional> {
  constructor(private service: EventoOperacionalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventoOperacional> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eventoOperacional: HttpResponse<EventoOperacional>) => {
          if (eventoOperacional.body) {
            return of(eventoOperacional.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EventoOperacional());
  }
}

export const eventoOperacionalRoute: Routes = [
  {
    path: '',
    component: EventoOperacionalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gatewayApp.sgqEventoOperacional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventoOperacionalDetailComponent,
    resolve: {
      eventoOperacional: EventoOperacionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqEventoOperacional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventoOperacionalUpdateComponent,
    resolve: {
      eventoOperacional: EventoOperacionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqEventoOperacional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventoOperacionalUpdateComponent,
    resolve: {
      eventoOperacional: EventoOperacionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gatewayApp.sgqEventoOperacional.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
