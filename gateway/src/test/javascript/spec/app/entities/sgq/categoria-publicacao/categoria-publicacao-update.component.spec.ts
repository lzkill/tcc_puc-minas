import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { CategoriaPublicacaoUpdateComponent } from 'app/entities/sgq/categoria-publicacao/categoria-publicacao-update.component';
import { CategoriaPublicacaoService } from 'app/entities/sgq/categoria-publicacao/categoria-publicacao.service';
import { CategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';

describe('Component Tests', () => {
  describe('CategoriaPublicacao Management Update Component', () => {
    let comp: CategoriaPublicacaoUpdateComponent;
    let fixture: ComponentFixture<CategoriaPublicacaoUpdateComponent>;
    let service: CategoriaPublicacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [CategoriaPublicacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategoriaPublicacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriaPublicacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaPublicacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoriaPublicacao(123);
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
        const entity = new CategoriaPublicacao();
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
