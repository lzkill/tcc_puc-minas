import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { AuditoriaUpdateComponent } from 'app/entities/sgq/auditoria/auditoria-update.component';
import { AuditoriaService } from 'app/entities/sgq/auditoria/auditoria.service';
import { Auditoria } from 'app/shared/model/sgq/auditoria.model';

describe('Component Tests', () => {
  describe('Auditoria Management Update Component', () => {
    let comp: AuditoriaUpdateComponent;
    let fixture: ComponentFixture<AuditoriaUpdateComponent>;
    let service: AuditoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [AuditoriaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AuditoriaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditoriaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditoriaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Auditoria(123);
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
        const entity = new Auditoria();
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
