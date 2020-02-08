import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';

@Component({
  selector: 'jhi-resultado-checklist-detail',
  templateUrl: './resultado-checklist-detail.component.html'
})
export class ResultadoChecklistDetailComponent implements OnInit {
  resultadoChecklist: IResultadoChecklist | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoChecklist }) => {
      this.resultadoChecklist = resultadoChecklist;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
