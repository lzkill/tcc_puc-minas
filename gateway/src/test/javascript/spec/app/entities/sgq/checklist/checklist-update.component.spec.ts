import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ChecklistUpdateComponent } from 'app/entities/sgq/checklist/checklist-update.component';
import { ChecklistService } from 'app/entities/sgq/checklist/checklist.service';
import { Checklist } from 'app/shared/model/sgq/checklist.model';

describe('Component Tests', () => {
  describe('Checklist Management Update Component', () => {
    let comp: ChecklistUpdateComponent;
    let fixture: ComponentFixture<ChecklistUpdateComponent>;
    let service: ChecklistService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ChecklistUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ChecklistUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChecklistUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChecklistService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Checklist(123);
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
        const entity = new Checklist();
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
