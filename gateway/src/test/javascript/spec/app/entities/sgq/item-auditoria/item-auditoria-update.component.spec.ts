import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ItemAuditoriaUpdateComponent } from 'app/entities/sgq/item-auditoria/item-auditoria-update.component';
import { ItemAuditoriaService } from 'app/entities/sgq/item-auditoria/item-auditoria.service';
import { ItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';

describe('Component Tests', () => {
  describe('ItemAuditoria Management Update Component', () => {
    let comp: ItemAuditoriaUpdateComponent;
    let fixture: ComponentFixture<ItemAuditoriaUpdateComponent>;
    let service: ItemAuditoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ItemAuditoriaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ItemAuditoriaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemAuditoriaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemAuditoriaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemAuditoria(123);
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
        const entity = new ItemAuditoria();
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
