import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { BoletimInformativoDetailComponent } from 'app/entities/sgq/boletim-informativo/boletim-informativo-detail.component';
import { BoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';

describe('Component Tests', () => {
  describe('BoletimInformativo Management Detail Component', () => {
    let comp: BoletimInformativoDetailComponent;
    let fixture: ComponentFixture<BoletimInformativoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ boletimInformativo: new BoletimInformativo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [BoletimInformativoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BoletimInformativoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BoletimInformativoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load boletimInformativo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.boletimInformativo).toEqual(jasmine.objectContaining({ id: 123 }));
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
