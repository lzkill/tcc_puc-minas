import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { EmpresaConsultoriaUpdateComponent } from 'app/entities/sgq/empresa-consultoria/empresa-consultoria-update.component';
import { EmpresaConsultoriaService } from 'app/entities/sgq/empresa-consultoria/empresa-consultoria.service';
import { EmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';

describe('Component Tests', () => {
  describe('EmpresaConsultoria Management Update Component', () => {
    let comp: EmpresaConsultoriaUpdateComponent;
    let fixture: ComponentFixture<EmpresaConsultoriaUpdateComponent>;
    let service: EmpresaConsultoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [EmpresaConsultoriaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmpresaConsultoriaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmpresaConsultoriaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmpresaConsultoriaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmpresaConsultoria(123);
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
        const entity = new EmpresaConsultoria();
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
