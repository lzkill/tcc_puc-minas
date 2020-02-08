import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { AnaliseConsultoriaDetailComponent } from 'app/entities/sgq/analise-consultoria/analise-consultoria-detail.component';
import { AnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';

describe('Component Tests', () => {
  describe('AnaliseConsultoria Management Detail Component', () => {
    let comp: AnaliseConsultoriaDetailComponent;
    let fixture: ComponentFixture<AnaliseConsultoriaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ analiseConsultoria: new AnaliseConsultoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [AnaliseConsultoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnaliseConsultoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnaliseConsultoriaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load analiseConsultoria on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.analiseConsultoria).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
