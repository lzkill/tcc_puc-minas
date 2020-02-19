import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';
import { ItemPlanoAuditoriaService } from '../entities/sgq/item-plano-auditoria/item-plano-auditoria.service';

import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  itemPlanoAuditorias?: IItemPlanoAuditoria[];

  numberOfItensPlanoAuditoria = 10;
  itensPlanoAuditoriaPredicate = 'dataInicioPrevisto';
  itensPlanoAuditoriaAscending = true;

  moment = require('moment');

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    protected itemPlanoAuditoriaService: ItemPlanoAuditoriaService,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.loadPage();
  }

  loadPage(): void {
    if (!this.isAuthenticated()) {
      this.itemPlanoAuditoriaService
        .query({
          'dataInicioPrevisto.greaterThan': this.moment.utc().format('YYYY-MM-DD[T]HH:mm:ss[Z]'),
          size: this.numberOfItensPlanoAuditoria,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IItemPlanoAuditoria[]>) => this.onSuccess(res.body),
          () => this.onError()
        );
    }
  }

  sort(): string[] {
    const result = [this.itensPlanoAuditoriaPredicate + ',' + (this.itensPlanoAuditoriaAscending ? 'asc' : 'desc')];
    if (this.itensPlanoAuditoriaPredicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  onSuccess(data: IItemPlanoAuditoria[] | null): void {
    this.itemPlanoAuditorias = data ? data : [];
  }

  onError(): void {}

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
