import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpresa } from 'app/shared/model/sgq/empresa.model';

@Component({
  selector: 'jhi-empresa-detail',
  templateUrl: './empresa-detail.component.html'
})
export class EmpresaDetailComponent implements OnInit {
  empresa: IEmpresa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.empresa = empresa;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
