import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConsultoria } from 'app/shared/model/sgq/consultoria.model';

@Component({
  selector: 'jhi-consultoria-detail',
  templateUrl: './consultoria-detail.component.html'
})
export class ConsultoriaDetailComponent implements OnInit {
  consultoria: IConsultoria | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consultoria }) => {
      this.consultoria = consultoria;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
