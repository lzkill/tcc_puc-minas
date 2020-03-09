import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { EmpresaDetailComponent } from 'app/entities/sgq/empresa/empresa-detail.component';
import { Empresa } from 'app/shared/model/sgq/empresa.model';

describe('Component Tests', () => {
  describe('Empresa Management Detail Component', () => {
    let comp: EmpresaDetailComponent;
    let fixture: ComponentFixture<EmpresaDetailComponent>;
    const route = ({ data: of({ empresa: new Empresa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [EmpresaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmpresaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmpresaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load empresa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.empresa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
