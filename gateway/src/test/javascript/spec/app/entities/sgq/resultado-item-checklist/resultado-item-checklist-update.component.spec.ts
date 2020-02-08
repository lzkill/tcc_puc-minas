import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ResultadoItemChecklistUpdateComponent } from 'app/entities/sgq/resultado-item-checklist/resultado-item-checklist-update.component';
import { ResultadoItemChecklistService } from 'app/entities/sgq/resultado-item-checklist/resultado-item-checklist.service';
import { ResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';

describe('Component Tests', () => {
  describe('ResultadoItemChecklist Management Update Component', () => {
    let comp: ResultadoItemChecklistUpdateComponent;
    let fixture: ComponentFixture<ResultadoItemChecklistUpdateComponent>;
    let service: ResultadoItemChecklistService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ResultadoItemChecklistUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResultadoItemChecklistUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultadoItemChecklistUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultadoItemChecklistService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultadoItemChecklist(123);
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
        const entity = new ResultadoItemChecklist();
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
