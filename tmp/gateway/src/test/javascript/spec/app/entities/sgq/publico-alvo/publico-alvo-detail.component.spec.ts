import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { PublicoAlvoDetailComponent } from 'app/entities/sgq/publico-alvo/publico-alvo-detail.component';
import { PublicoAlvo } from 'app/shared/model/sgq/publico-alvo.model';

describe('Component Tests', () => {
  describe('PublicoAlvo Management Detail Component', () => {
    let comp: PublicoAlvoDetailComponent;
    let fixture: ComponentFixture<PublicoAlvoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ publicoAlvo: new PublicoAlvo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [PublicoAlvoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PublicoAlvoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PublicoAlvoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load publicoAlvo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.publicoAlvo).toEqual(jasmine.objectContaining({ id: 123 }));
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
