import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ResultadoChecklistService } from './resultado-checklist.service';
import { ResultadoChecklistDeleteDialogComponent } from './resultado-checklist-delete-dialog.component';

@Component({
  selector: 'jhi-resultado-checklist',
  templateUrl: './resultado-checklist.component.html'
})
export class ResultadoChecklistComponent implements OnInit, OnDestroy {
  resultadoChecklists?: IResultadoChecklist[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected resultadoChecklistService: ResultadoChecklistService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page ? page : this.page;
    this.resultadoChecklistService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IResultadoChecklist[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInResultadoChecklists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IResultadoChecklist): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInResultadoChecklists(): void {
    this.eventSubscriber = this.eventManager.subscribe('resultadoChecklistListModification', () => this.loadPage());
  }

  delete(resultadoChecklist: IResultadoChecklist): void {
    const modalRef = this.modalService.open(ResultadoChecklistDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resultadoChecklist = resultadoChecklist;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IResultadoChecklist[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/resultado-checklist'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.resultadoChecklists = data ? data : [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
