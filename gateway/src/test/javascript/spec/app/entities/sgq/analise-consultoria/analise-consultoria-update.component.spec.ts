import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { AnaliseConsultoriaUpdateComponent } from 'app/entities/sgq/analise-consultoria/analise-consultoria-update.component';
import { AnaliseConsultoriaService } from 'app/entities/sgq/analise-consultoria/analise-consultoria.service';
import { AnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';

describe('Component Tests', () => {
  describe('AnaliseConsultoria Management Update Component', () => {
    let comp: AnaliseConsultoriaUpdateComponent;
    let fixture: ComponentFixture<AnaliseConsultoriaUpdateComponent>;
    let service: AnaliseConsultoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [AnaliseConsultoriaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnaliseConsultoriaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnaliseConsultoriaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnaliseConsultoriaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnaliseConsultoria(123);
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
        const entity = new AnaliseConsultoria();
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
