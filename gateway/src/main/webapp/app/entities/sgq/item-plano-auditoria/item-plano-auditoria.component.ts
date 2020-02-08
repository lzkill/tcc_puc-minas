import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ItemPlanoAuditoriaService } from './item-plano-auditoria.service';
import { ItemPlanoAuditoriaDeleteDialogComponent } from './item-plano-auditoria-delete-dialog.component';

@Component({
  selector: 'jhi-item-plano-auditoria',
  templateUrl: './item-plano-auditoria.component.html'
})
export class ItemPlanoAuditoriaComponent implements OnInit, OnDestroy {
  itemPlanoAuditorias?: IItemPlanoAuditoria[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected itemPlanoAuditoriaService: ItemPlanoAuditoriaService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page ? page : this.page;
    this.itemPlanoAuditoriaService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IItemPlanoAuditoria[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInItemPlanoAuditorias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IItemPlanoAuditoria): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInItemPlanoAuditorias(): void {
    this.eventSubscriber = this.eventManager.subscribe('itemPlanoAuditoriaListModification', () => this.loadPage());
  }

  delete(itemPlanoAuditoria: IItemPlanoAuditoria): void {
    const modalRef = this.modalService.open(ItemPlanoAuditoriaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.itemPlanoAuditoria = itemPlanoAuditoria;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IItemPlanoAuditoria[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/item-plano-auditoria'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.itemPlanoAuditorias = data ? data : [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
