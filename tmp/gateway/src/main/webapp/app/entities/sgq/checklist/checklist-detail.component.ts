import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChecklist } from 'app/shared/model/sgq/checklist.model';

@Component({
  selector: 'jhi-checklist-detail',
  templateUrl: './checklist-detail.component.html'
})
export class ChecklistDetailComponent implements OnInit {
  checklist: IChecklist | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checklist }) => {
      this.checklist = checklist;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
