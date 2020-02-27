import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ConsultoriaUpdateComponent } from 'app/entities/sgq/consultoria/consultoria-update.component';
import { ConsultoriaService } from 'app/entities/sgq/consultoria/consultoria.service';
import { Consultoria } from 'app/shared/model/sgq/consultoria.model';

describe('Component Tests', () => {
  describe('Consultoria Management Update Component', () => {
    let comp: ConsultoriaUpdateComponent;
    let fixture: ComponentFixture<ConsultoriaUpdateComponent>;
    let service: ConsultoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ConsultoriaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ConsultoriaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConsultoriaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConsultoriaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Consultoria(123);
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
        const entity = new Consultoria();
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
