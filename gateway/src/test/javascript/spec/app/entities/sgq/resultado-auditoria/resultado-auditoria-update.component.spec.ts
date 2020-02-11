import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ResultadoAuditoriaUpdateComponent } from 'app/entities/sgq/resultado-auditoria/resultado-auditoria-update.component';
import { ResultadoAuditoriaService } from 'app/entities/sgq/resultado-auditoria/resultado-auditoria.service';
import { ResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';

describe('Component Tests', () => {
  describe('ResultadoAuditoria Management Update Component', () => {
    let comp: ResultadoAuditoriaUpdateComponent;
    let fixture: ComponentFixture<ResultadoAuditoriaUpdateComponent>;
    let service: ResultadoAuditoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ResultadoAuditoriaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResultadoAuditoriaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultadoAuditoriaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultadoAuditoriaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultadoAuditoria(123);
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
        const entity = new ResultadoAuditoria();
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
