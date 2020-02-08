import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { PublicoAlvoUpdateComponent } from 'app/entities/sgq/publico-alvo/publico-alvo-update.component';
import { PublicoAlvoService } from 'app/entities/sgq/publico-alvo/publico-alvo.service';
import { PublicoAlvo } from 'app/shared/model/sgq/publico-alvo.model';

describe('Component Tests', () => {
  describe('PublicoAlvo Management Update Component', () => {
    let comp: PublicoAlvoUpdateComponent;
    let fixture: ComponentFixture<PublicoAlvoUpdateComponent>;
    let service: PublicoAlvoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [PublicoAlvoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PublicoAlvoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PublicoAlvoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PublicoAlvoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PublicoAlvo(123);
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
        const entity = new PublicoAlvo();
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
