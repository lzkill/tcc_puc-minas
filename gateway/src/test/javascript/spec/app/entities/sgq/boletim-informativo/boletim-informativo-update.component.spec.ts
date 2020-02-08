import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { BoletimInformativoUpdateComponent } from 'app/entities/sgq/boletim-informativo/boletim-informativo-update.component';
import { BoletimInformativoService } from 'app/entities/sgq/boletim-informativo/boletim-informativo.service';
import { BoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';

describe('Component Tests', () => {
  describe('BoletimInformativo Management Update Component', () => {
    let comp: BoletimInformativoUpdateComponent;
    let fixture: ComponentFixture<BoletimInformativoUpdateComponent>;
    let service: BoletimInformativoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [BoletimInformativoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BoletimInformativoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoletimInformativoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoletimInformativoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BoletimInformativo(123);
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
        const entity = new BoletimInformativo();
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
