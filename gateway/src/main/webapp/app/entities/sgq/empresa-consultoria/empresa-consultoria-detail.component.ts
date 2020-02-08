import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';

@Component({
  selector: 'jhi-empresa-consultoria-detail',
  templateUrl: './empresa-consultoria-detail.component.html'
})
export class EmpresaConsultoriaDetailComponent implements OnInit {
  empresaConsultoria: IEmpresaConsultoria | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresaConsultoria }) => {
      this.empresaConsultoria = empresaConsultoria;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
