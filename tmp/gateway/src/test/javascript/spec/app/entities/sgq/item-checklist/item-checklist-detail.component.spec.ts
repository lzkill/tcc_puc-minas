import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { ItemChecklistDetailComponent } from 'app/entities/sgq/item-checklist/item-checklist-detail.component';
import { ItemChecklist } from 'app/shared/model/sgq/item-checklist.model';

describe('Component Tests', () => {
  describe('ItemChecklist Management Detail Component', () => {
    let comp: ItemChecklistDetailComponent;
    let fixture: ComponentFixture<ItemChecklistDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ itemChecklist: new ItemChecklist(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ItemChecklistDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ItemChecklistDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemChecklistDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load itemChecklist on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemChecklist).toEqual(jasmine.objectContaining({ id: 123 }));
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
