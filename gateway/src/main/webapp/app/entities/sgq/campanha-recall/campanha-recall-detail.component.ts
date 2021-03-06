import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-campanha-recall-detail',
  templateUrl: './campanha-recall-detail.component.html'
})
export class CampanhaRecallDetailComponent implements OnInit {
  campanhaRecall: ICampanhaRecall | null = null;

  usuarios: Map<any, IUser> = new Map<any, IUser>();

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected userService: UserService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campanhaRecall }) => {
      this.campanhaRecall = campanhaRecall;

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

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
