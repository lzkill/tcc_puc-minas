import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProdutoNaoConformeService } from './produto-nao-conforme.service';
import { ProdutoNaoConformeDeleteDialogComponent } from './produto-nao-conforme-delete-dialog.component';

@Component({
  selector: 'jhi-produto-nao-conforme',
  templateUrl: './produto-nao-conforme.component.html'
})
export class ProdutoNaoConformeComponent implements OnInit, OnDestroy {
  produtoNaoConformes?: IProdutoNaoConforme[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected produtoNaoConformeService: ProdutoNaoConformeService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page ? page : this.page;
    this.produtoNaoConformeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProdutoNaoConforme[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInProdutoNaoConformes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProdutoNaoConforme): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInProdutoNaoConformes(): void {
    this.eventSubscriber = this.eventManager.subscribe('produtoNaoConformeListModification', () => this.loadPage());
  }

  delete(produtoNaoConforme: IProdutoNaoConforme): void {
    const modalRef = this.modalService.open(ProdutoNaoConformeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.produtoNaoConforme = produtoNaoConforme;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IProdutoNaoConforme[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/produto-nao-conforme'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.produtoNaoConformes = data ? data : [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
