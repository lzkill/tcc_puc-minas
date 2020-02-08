import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';

@Component({
  selector: 'jhi-item-checklist-detail',
  templateUrl: './item-checklist-detail.component.html'
})
export class ItemChecklistDetailComponent implements OnInit {
  itemChecklist: IItemChecklist | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemChecklist }) => {
      this.itemChecklist = itemChecklist;
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
