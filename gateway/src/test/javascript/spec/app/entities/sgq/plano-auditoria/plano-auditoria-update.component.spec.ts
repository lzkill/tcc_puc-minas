import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { PlanoAuditoriaUpdateComponent } from 'app/entities/sgq/plano-auditoria/plano-auditoria-update.component';
import { PlanoAuditoriaService } from 'app/entities/sgq/plano-auditoria/plano-auditoria.service';
import { PlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';

describe('Component Tests', () => {
  describe('PlanoAuditoria Management Update Component', () => {
    let comp: PlanoAuditoriaUpdateComponent;
    let fixture: ComponentFixture<PlanoAuditoriaUpdateComponent>;
    let service: PlanoAuditoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [PlanoAuditoriaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlanoAuditoriaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoAuditoriaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoAuditoriaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoAuditoria(123);
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
        const entity = new PlanoAuditoria();
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
