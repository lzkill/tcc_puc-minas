import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SetorDetailComponent } from 'app/entities/sgq/setor/setor-detail.component';
import { Setor } from 'app/shared/model/sgq/setor.model';

describe('Component Tests', () => {
  describe('Setor Management Detail Component', () => {
    let comp: SetorDetailComponent;
    let fixture: ComponentFixture<SetorDetailComponent>;
    const route = ({ data: of({ setor: new Setor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SetorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SetorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SetorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load setor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.setor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
