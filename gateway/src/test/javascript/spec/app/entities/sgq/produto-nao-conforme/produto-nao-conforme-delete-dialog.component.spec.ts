import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { ProdutoNaoConformeDeleteDialogComponent } from 'app/entities/sgq/produto-nao-conforme/produto-nao-conforme-delete-dialog.component';
import { ProdutoNaoConformeService } from 'app/entities/sgq/produto-nao-conforme/produto-nao-conforme.service';

describe('Component Tests', () => {
  describe('ProdutoNaoConforme Management Delete Component', () => {
    let comp: ProdutoNaoConformeDeleteDialogComponent;
    let fixture: ComponentFixture<ProdutoNaoConformeDeleteDialogComponent>;
    let service: ProdutoNaoConformeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ProdutoNaoConformeDeleteDialogComponent]
      })
        .overrideTemplate(ProdutoNaoConformeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProdutoNaoConformeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProdutoNaoConformeService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
