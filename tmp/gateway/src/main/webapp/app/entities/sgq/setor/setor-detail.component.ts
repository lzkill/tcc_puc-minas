import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISetor } from 'app/shared/model/sgq/setor.model';

@Component({
  selector: 'jhi-setor-detail',
  templateUrl: './setor-detail.component.html'
})
export class SetorDetailComponent implements OnInit {
  setor: ISetor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ setor }) => {
      this.setor = setor;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
