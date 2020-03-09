import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ItemPlanoAuditoriaUpdateComponent } from 'app/entities/sgq/item-plano-auditoria/item-plano-auditoria-update.component';
import { ItemPlanoAuditoriaService } from 'app/entities/sgq/item-plano-auditoria/item-plano-auditoria.service';
import { ItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

describe('Component Tests', () => {
  describe('ItemPlanoAuditoria Management Update Component', () => {
    let comp: ItemPlanoAuditoriaUpdateComponent;
    let fixture: ComponentFixture<ItemPlanoAuditoriaUpdateComponent>;
    let service: ItemPlanoAuditoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ItemPlanoAuditoriaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ItemPlanoAuditoriaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemPlanoAuditoriaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemPlanoAuditoriaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemPlanoAuditoria(123);
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
        const entity = new ItemPlanoAuditoria();
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
