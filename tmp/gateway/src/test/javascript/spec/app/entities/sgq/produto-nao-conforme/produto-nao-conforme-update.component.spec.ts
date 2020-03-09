import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ProdutoNaoConformeUpdateComponent } from 'app/entities/sgq/produto-nao-conforme/produto-nao-conforme-update.component';
import { ProdutoNaoConformeService } from 'app/entities/sgq/produto-nao-conforme/produto-nao-conforme.service';
import { ProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';

describe('Component Tests', () => {
  describe('ProdutoNaoConforme Management Update Component', () => {
    let comp: ProdutoNaoConformeUpdateComponent;
    let fixture: ComponentFixture<ProdutoNaoConformeUpdateComponent>;
    let service: ProdutoNaoConformeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ProdutoNaoConformeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProdutoNaoConformeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProdutoNaoConformeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProdutoNaoConformeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProdutoNaoConforme(123);
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
        const entity = new ProdutoNaoConforme();
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
