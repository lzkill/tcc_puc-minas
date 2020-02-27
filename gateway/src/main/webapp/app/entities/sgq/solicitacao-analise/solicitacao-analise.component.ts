import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SolicitacaoAnaliseService } from './solicitacao-analise.service';
import { SolicitacaoAnaliseDeleteDialogComponent } from './solicitacao-analise-delete-dialog.component';

@Component({
  selector: 'jhi-solicitacao-analise',
  templateUrl: './solicitacao-analise.component.html'
})
export class SolicitacaoAnaliseComponent implements OnInit, OnDestroy {
  solicitacaoAnalises?: ISolicitacaoAnalise[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected solicitacaoAnaliseService: SolicitacaoAnaliseService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page ? page : this.page;
    this.solicitacaoAnaliseService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISolicitacaoAnalise[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInSolicitacaoAnalises();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISolicitacaoAnalise): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSolicitacaoAnalises(): void {
    this.eventSubscriber = this.eventManager.subscribe('solicitacaoAnaliseListModification', () => this.loadPage());
  }

  delete(solicitacaoAnalise: ISolicitacaoAnalise): void {
    const modalRef = this.modalService.open(SolicitacaoAnaliseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.solicitacaoAnalise = solicitacaoAnalise;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISolicitacaoAnalise[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/solicitacao-analise'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.solicitacaoAnalises = data ? data : [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
