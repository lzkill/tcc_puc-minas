import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ProcessoUpdateComponent } from 'app/entities/sgq/processo/processo-update.component';
import { ProcessoService } from 'app/entities/sgq/processo/processo.service';
import { Processo } from 'app/shared/model/sgq/processo.model';

describe('Component Tests', () => {
  describe('Processo Management Update Component', () => {
    let comp: ProcessoUpdateComponent;
    let fixture: ComponentFixture<ProcessoUpdateComponent>;
    let service: ProcessoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ProcessoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProcessoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProcessoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Processo(123);
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
        const entity = new Processo();
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
