import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-solicitacao-analise-detail',
  templateUrl: './solicitacao-analise-detail.component.html'
})
export class SolicitacaoAnaliseDetailComponent implements OnInit {
  solicitacaoAnalise: ISolicitacaoAnalise | null = null;

  usuarios: Map<any, IUser> = new Map<any, IUser>();

  constructor(protected activatedRoute: ActivatedRoute, protected userService: UserService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ solicitacaoAnalise }) => {
      this.solicitacaoAnalise = solicitacaoAnalise;

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) =>
          resBody.forEach(item => {
            if (!this.userService.isAdmin(item)) this.usuarios.set(item.id, item);
          })
        );
    });
  }

  previousState(): void {
    window.history.back();
  }
}
