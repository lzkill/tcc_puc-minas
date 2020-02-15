import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-resultado-checklist-detail',
  templateUrl: './resultado-checklist-detail.component.html'
})
export class ResultadoChecklistDetailComponent implements OnInit {
  resultadoChecklist: IResultadoChecklist | null = null;

  usuarios: Map<any, IUser> = new Map<any, IUser>();

  constructor(protected activatedRoute: ActivatedRoute, protected userService: UserService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoChecklist }) => {
      this.resultadoChecklist = resultadoChecklist;

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
