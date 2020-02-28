import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CampanhaRecallService } from './campanha-recall.service';
import { CampanhaRecallDeleteDialogComponent } from './campanha-recall-delete-dialog.component';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-campanha-recall',
  templateUrl: './campanha-recall.component.html'
})
export class CampanhaRecallComponent implements OnInit, OnDestroy {
  campanhaRecalls?: ICampanhaRecall[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  usuarios: Map<any, IUser> = new Map<any, IUser>();

  constructor(
    protected campanhaRecallService: CampanhaRecallService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected userService: UserService
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page ? page : this.page;
    this.campanhaRecallService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICampanhaRecall[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => resBody.forEach(item => this.usuarios.set(item.id, item)));

      this.loadPage();
    });
    this.registerChangeInCampanhaRecalls();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICampanhaRecall): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInCampanhaRecalls(): void {
    this.eventSubscriber = this.eventManager.subscribe('campanhaRecallListModification', () => this.loadPage());
  }

  delete(campanhaRecall: ICampanhaRecall): void {
    const modalRef = this.modalService.open(CampanhaRecallDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.campanhaRecall = campanhaRecall;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICampanhaRecall[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/campanha-recall'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.campanhaRecalls = data ? data : [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
