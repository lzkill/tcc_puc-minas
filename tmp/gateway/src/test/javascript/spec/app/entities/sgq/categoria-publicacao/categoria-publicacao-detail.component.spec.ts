import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { CategoriaPublicacaoDetailComponent } from 'app/entities/sgq/categoria-publicacao/categoria-publicacao-detail.component';
import { CategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';

describe('Component Tests', () => {
  describe('CategoriaPublicacao Management Detail Component', () => {
    let comp: CategoriaPublicacaoDetailComponent;
    let fixture: ComponentFixture<CategoriaPublicacaoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ categoriaPublicacao: new CategoriaPublicacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [CategoriaPublicacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriaPublicacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaPublicacaoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load categoriaPublicacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoriaPublicacao).toEqual(jasmine.objectContaining({ id: 123 }));
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
