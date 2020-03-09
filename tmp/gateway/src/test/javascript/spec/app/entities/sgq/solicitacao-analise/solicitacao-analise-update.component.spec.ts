import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SolicitacaoAnaliseUpdateComponent } from 'app/entities/sgq/solicitacao-analise/solicitacao-analise-update.component';
import { SolicitacaoAnaliseService } from 'app/entities/sgq/solicitacao-analise/solicitacao-analise.service';
import { SolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';

describe('Component Tests', () => {
  describe('SolicitacaoAnalise Management Update Component', () => {
    let comp: SolicitacaoAnaliseUpdateComponent;
    let fixture: ComponentFixture<SolicitacaoAnaliseUpdateComponent>;
    let service: SolicitacaoAnaliseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SolicitacaoAnaliseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SolicitacaoAnaliseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SolicitacaoAnaliseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SolicitacaoAnaliseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SolicitacaoAnalise(123);
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
        const entity = new SolicitacaoAnalise();
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
