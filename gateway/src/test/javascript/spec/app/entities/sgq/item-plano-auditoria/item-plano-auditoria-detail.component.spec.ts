import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { ItemPlanoAuditoriaDetailComponent } from 'app/entities/sgq/item-plano-auditoria/item-plano-auditoria-detail.component';
import { ItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

describe('Component Tests', () => {
  describe('ItemPlanoAuditoria Management Detail Component', () => {
    let comp: ItemPlanoAuditoriaDetailComponent;
    let fixture: ComponentFixture<ItemPlanoAuditoriaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ itemPlanoAuditoria: new ItemPlanoAuditoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ItemPlanoAuditoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ItemPlanoAuditoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemPlanoAuditoriaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load itemPlanoAuditoria on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemPlanoAuditoria).toEqual(jasmine.objectContaining({ id: 123 }));
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
