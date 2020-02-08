import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ItemPlanoAuditoriaDetailComponent } from 'app/entities/sgq/item-plano-auditoria/item-plano-auditoria-detail.component';
import { ItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

describe('Component Tests', () => {
  describe('ItemPlanoAuditoria Management Detail Component', () => {
    let comp: ItemPlanoAuditoriaDetailComponent;
    let fixture: ComponentFixture<ItemPlanoAuditoriaDetailComponent>;
    const route = ({ data: of({ itemPlanoAuditoria: new ItemPlanoAuditoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ItemPlanoAuditoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ItemPlanoAuditoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemPlanoAuditoriaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load itemPlanoAuditoria on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemPlanoAuditoria).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
