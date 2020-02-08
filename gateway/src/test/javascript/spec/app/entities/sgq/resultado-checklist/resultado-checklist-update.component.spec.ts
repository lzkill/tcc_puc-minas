import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ResultadoChecklistUpdateComponent } from 'app/entities/sgq/resultado-checklist/resultado-checklist-update.component';
import { ResultadoChecklistService } from 'app/entities/sgq/resultado-checklist/resultado-checklist.service';
import { ResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';

describe('Component Tests', () => {
  describe('ResultadoChecklist Management Update Component', () => {
    let comp: ResultadoChecklistUpdateComponent;
    let fixture: ComponentFixture<ResultadoChecklistUpdateComponent>;
    let service: ResultadoChecklistService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ResultadoChecklistUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResultadoChecklistUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultadoChecklistUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultadoChecklistService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultadoChecklist(123);
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
        const entity = new ResultadoChecklist();
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
