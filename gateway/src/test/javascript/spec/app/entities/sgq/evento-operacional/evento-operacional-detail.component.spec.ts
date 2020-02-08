import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { EventoOperacionalDetailComponent } from 'app/entities/sgq/evento-operacional/evento-operacional-detail.component';
import { EventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';

describe('Component Tests', () => {
  describe('EventoOperacional Management Detail Component', () => {
    let comp: EventoOperacionalDetailComponent;
    let fixture: ComponentFixture<EventoOperacionalDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ eventoOperacional: new EventoOperacional(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [EventoOperacionalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventoOperacionalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventoOperacionalDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load eventoOperacional on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventoOperacional).toEqual(jasmine.objectContaining({ id: 123 }));
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
