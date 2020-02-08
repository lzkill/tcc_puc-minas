import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { EmpresaConsultoriaDetailComponent } from 'app/entities/sgq/empresa-consultoria/empresa-consultoria-detail.component';
import { EmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';

describe('Component Tests', () => {
  describe('EmpresaConsultoria Management Detail Component', () => {
    let comp: EmpresaConsultoriaDetailComponent;
    let fixture: ComponentFixture<EmpresaConsultoriaDetailComponent>;
    const route = ({ data: of({ empresaConsultoria: new EmpresaConsultoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [EmpresaConsultoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmpresaConsultoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmpresaConsultoriaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load empresaConsultoria on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.empresaConsultoria).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
