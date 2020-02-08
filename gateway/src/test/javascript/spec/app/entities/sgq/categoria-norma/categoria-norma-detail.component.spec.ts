import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { CategoriaNormaDetailComponent } from 'app/entities/sgq/categoria-norma/categoria-norma-detail.component';
import { CategoriaNorma } from 'app/shared/model/sgq/categoria-norma.model';

describe('Component Tests', () => {
  describe('CategoriaNorma Management Detail Component', () => {
    let comp: CategoriaNormaDetailComponent;
    let fixture: ComponentFixture<CategoriaNormaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ categoriaNorma: new CategoriaNorma(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [CategoriaNormaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriaNormaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaNormaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load categoriaNorma on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoriaNorma).toEqual(jasmine.objectContaining({ id: 123 }));
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
