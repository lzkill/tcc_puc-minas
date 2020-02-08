import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { AcaoSGQUpdateComponent } from 'app/entities/sgq/acao-sgq/acao-sgq-update.component';
import { AcaoSGQService } from 'app/entities/sgq/acao-sgq/acao-sgq.service';
import { AcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';

describe('Component Tests', () => {
  describe('AcaoSGQ Management Update Component', () => {
    let comp: AcaoSGQUpdateComponent;
    let fixture: ComponentFixture<AcaoSGQUpdateComponent>;
    let service: AcaoSGQService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [AcaoSGQUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AcaoSGQUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AcaoSGQUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AcaoSGQService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AcaoSGQ(123);
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
        const entity = new AcaoSGQ();
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
