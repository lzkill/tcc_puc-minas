import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { ResultadoAuditoriaDetailComponent } from 'app/entities/sgq/resultado-auditoria/resultado-auditoria-detail.component';
import { ResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';

describe('Component Tests', () => {
  describe('ResultadoAuditoria Management Detail Component', () => {
    let comp: ResultadoAuditoriaDetailComponent;
    let fixture: ComponentFixture<ResultadoAuditoriaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ resultadoAuditoria: new ResultadoAuditoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ResultadoAuditoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResultadoAuditoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultadoAuditoriaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load resultadoAuditoria on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultadoAuditoria).toEqual(jasmine.objectContaining({ id: 123 }));
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
