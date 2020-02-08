import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { ProdutoNaoConformeDetailComponent } from 'app/entities/sgq/produto-nao-conforme/produto-nao-conforme-detail.component';
import { ProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';

describe('Component Tests', () => {
  describe('ProdutoNaoConforme Management Detail Component', () => {
    let comp: ProdutoNaoConformeDetailComponent;
    let fixture: ComponentFixture<ProdutoNaoConformeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ produtoNaoConforme: new ProdutoNaoConforme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ProdutoNaoConformeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProdutoNaoConformeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProdutoNaoConformeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load produtoNaoConforme on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.produtoNaoConforme).toEqual(jasmine.objectContaining({ id: 123 }));
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
