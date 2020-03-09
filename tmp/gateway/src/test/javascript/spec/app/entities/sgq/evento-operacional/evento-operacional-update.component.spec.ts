import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { EventoOperacionalUpdateComponent } from 'app/entities/sgq/evento-operacional/evento-operacional-update.component';
import { EventoOperacionalService } from 'app/entities/sgq/evento-operacional/evento-operacional.service';
import { EventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';

describe('Component Tests', () => {
  describe('EventoOperacional Management Update Component', () => {
    let comp: EventoOperacionalUpdateComponent;
    let fixture: ComponentFixture<EventoOperacionalUpdateComponent>;
    let service: EventoOperacionalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [EventoOperacionalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventoOperacionalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventoOperacionalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventoOperacionalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventoOperacional(123);
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
        const entity = new EventoOperacional();
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
