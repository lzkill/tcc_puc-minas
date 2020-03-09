import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { NaoConformidadeUpdateComponent } from 'app/entities/sgq/nao-conformidade/nao-conformidade-update.component';
import { NaoConformidadeService } from 'app/entities/sgq/nao-conformidade/nao-conformidade.service';
import { NaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';

describe('Component Tests', () => {
  describe('NaoConformidade Management Update Component', () => {
    let comp: NaoConformidadeUpdateComponent;
    let fixture: ComponentFixture<NaoConformidadeUpdateComponent>;
    let service: NaoConformidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [NaoConformidadeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NaoConformidadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NaoConformidadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NaoConformidadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NaoConformidade(123);
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
        const entity = new NaoConformidade();
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
