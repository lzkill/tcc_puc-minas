import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ItemChecklistUpdateComponent } from 'app/entities/sgq/item-checklist/item-checklist-update.component';
import { ItemChecklistService } from 'app/entities/sgq/item-checklist/item-checklist.service';
import { ItemChecklist } from 'app/shared/model/sgq/item-checklist.model';

describe('Component Tests', () => {
  describe('ItemChecklist Management Update Component', () => {
    let comp: ItemChecklistUpdateComponent;
    let fixture: ComponentFixture<ItemChecklistUpdateComponent>;
    let service: ItemChecklistService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ItemChecklistUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ItemChecklistUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemChecklistUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemChecklistService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemChecklist(123);
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
        const entity = new ItemChecklist();
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
