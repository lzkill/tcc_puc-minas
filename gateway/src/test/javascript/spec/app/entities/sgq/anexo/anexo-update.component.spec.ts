import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { AnexoUpdateComponent } from 'app/entities/sgq/anexo/anexo-update.component';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { Anexo } from 'app/shared/model/sgq/anexo.model';

describe('Component Tests', () => {
  describe('Anexo Management Update Component', () => {
    let comp: AnexoUpdateComponent;
    let fixture: ComponentFixture<AnexoUpdateComponent>;
    let service: AnexoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [AnexoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnexoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnexoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnexoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Anexo(123);
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
        const entity = new Anexo();
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
