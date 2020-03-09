import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ChecklistDetailComponent } from 'app/entities/sgq/checklist/checklist-detail.component';
import { Checklist } from 'app/shared/model/sgq/checklist.model';

describe('Component Tests', () => {
  describe('Checklist Management Detail Component', () => {
    let comp: ChecklistDetailComponent;
    let fixture: ComponentFixture<ChecklistDetailComponent>;
    const route = ({ data: of({ checklist: new Checklist(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ChecklistDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ChecklistDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChecklistDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load checklist on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checklist).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
