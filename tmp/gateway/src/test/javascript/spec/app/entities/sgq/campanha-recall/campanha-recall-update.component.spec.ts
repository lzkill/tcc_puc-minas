import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { CampanhaRecallUpdateComponent } from 'app/entities/sgq/campanha-recall/campanha-recall-update.component';
import { CampanhaRecallService } from 'app/entities/sgq/campanha-recall/campanha-recall.service';
import { CampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';

describe('Component Tests', () => {
  describe('CampanhaRecall Management Update Component', () => {
    let comp: CampanhaRecallUpdateComponent;
    let fixture: ComponentFixture<CampanhaRecallUpdateComponent>;
    let service: CampanhaRecallService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [CampanhaRecallUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CampanhaRecallUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CampanhaRecallUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampanhaRecallService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CampanhaRecall(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CampanhaRecall();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
