import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { ResultadoItemChecklistDetailComponent } from 'app/entities/sgq/resultado-item-checklist/resultado-item-checklist-detail.component';
import { ResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';

describe('Component Tests', () => {
  describe('ResultadoItemChecklist Management Detail Component', () => {
    let comp: ResultadoItemChecklistDetailComponent;
    let fixture: ComponentFixture<ResultadoItemChecklistDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ resultadoItemChecklist: new ResultadoItemChecklist(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ResultadoItemChecklistDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResultadoItemChecklistDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultadoItemChecklistDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load resultadoItemChecklist on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultadoItemChecklist).toEqual(jasmine.objectContaining({ id: 123 }));
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
