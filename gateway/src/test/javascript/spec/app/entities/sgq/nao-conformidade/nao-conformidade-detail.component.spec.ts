import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { NaoConformidadeDetailComponent } from 'app/entities/sgq/nao-conformidade/nao-conformidade-detail.component';
import { NaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';

describe('Component Tests', () => {
  describe('NaoConformidade Management Detail Component', () => {
    let comp: NaoConformidadeDetailComponent;
    let fixture: ComponentFixture<NaoConformidadeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ naoConformidade: new NaoConformidade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [NaoConformidadeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NaoConformidadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NaoConformidadeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load naoConformidade on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.naoConformidade).toEqual(jasmine.objectContaining({ id: 123 }));
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
