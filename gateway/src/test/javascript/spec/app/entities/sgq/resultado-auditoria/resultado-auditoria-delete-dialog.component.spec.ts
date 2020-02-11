import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { ResultadoAuditoriaDeleteDialogComponent } from 'app/entities/sgq/resultado-auditoria/resultado-auditoria-delete-dialog.component';
import { ResultadoAuditoriaService } from 'app/entities/sgq/resultado-auditoria/resultado-auditoria.service';

describe('Component Tests', () => {
  describe('ResultadoAuditoria Management Delete Component', () => {
    let comp: ResultadoAuditoriaDeleteDialogComponent;
    let fixture: ComponentFixture<ResultadoAuditoriaDeleteDialogComponent>;
    let service: ResultadoAuditoriaService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ResultadoAuditoriaDeleteDialogComponent]
      })
        .overrideTemplate(ResultadoAuditoriaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultadoAuditoriaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultadoAuditoriaService);
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
