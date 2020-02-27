import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';

@Component({
  selector: 'jhi-solicitacao-analise-detail',
  templateUrl: './solicitacao-analise-detail.component.html'
})
export class SolicitacaoAnaliseDetailComponent implements OnInit {
  solicitacaoAnalise: ISolicitacaoAnalise | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ solicitacaoAnalise }) => {
      this.solicitacaoAnalise = solicitacaoAnalise;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
